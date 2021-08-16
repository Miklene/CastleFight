package com.miklene.castlefight.mvvm

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.*
import com.miklene.castlefight.repositories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFightViewModel(@NonNull application: Application) : AndroidViewModel(application) {
    private val playerRepsitory: PlayerRepository
    private val raceRepository: RaceRepository
    private val fightRepository: FightRepository
    private val playerVsPlayerStatisticsRepository: PlayerVsPlayerStatisticsRepository
    private val raceVsRaceStatisticsRepository: RaceVsRaceStatisticsRepository
    private lateinit var playerLiveData: LiveData<List<Player>>
    private lateinit var raceLiveData: LiveData<List<Race>>

    init {
        playerRepsitory = PlayerRepository(application)
        raceRepository = RaceRepository(application)
        fightRepository = FightRepository(application)
        playerVsPlayerStatisticsRepository = PlayerVsPlayerStatisticsRepository(application)
        raceVsRaceStatisticsRepository = RaceVsRaceStatisticsRepository(application)
        viewModelScope.launch {
            playerLiveData = playerRepsitory.getAllPlayers()
            raceLiveData = raceRepository.getAllRaces()
        }
    }

    fun getAllPlayers(): LiveData<List<Player>> {
        return playerLiveData
    }

    fun getAllRaces(): LiveData<List<Race>> {
        return raceLiveData;
    }

    fun insertFight(fight: Fight) {
        CoroutineScope(Dispatchers.IO).launch {
            fightRepository.insert(fight)
            val playerWins = fightRepository.getPlayerWins(fight.winner).size
            val playerLoses = fightRepository.getPlayerLoses(fight.loser).size
            val raceWins = fightRepository.getRaceWins(fight.winnerRace).size
            val raceLoses = fightRepository.getRaceLoses(fight.loserRace).size
            playerRepsitory.updatePlayerWins(fight.winner, playerWins)
            playerRepsitory.updatePlayerLoses(fight.loser, playerLoses)
            raceRepository.updateRaceWins(fight.winnerRace, raceWins)
            raceRepository.updateRaceLoses(fight.loserRace, raceLoses)
            updatePlVsPlDatabase(fight.winner, fight.loser)
            updateRaceVsRaceDatabase(fight.winnerRace, fight.loserRace)
        }
    }

   private fun updatePlVsPlDatabase(winner:String, loser:String){
        if ( playerVsPlayerStatisticsRepository.getByPlayersNames(winner, loser) == null) {
            playerVsPlayerStatisticsRepository.insert(
                PlayerVsPlayerStatistics(0, winner, loser, 0, 0)
            )
            playerVsPlayerStatisticsRepository.insert(
                PlayerVsPlayerStatistics(0, loser, winner, 0, 0)
            )
        }
        val plVsPlStatWinner =
            playerVsPlayerStatisticsRepository.getByPlayersNames(winner, loser)
        plVsPlStatWinner.wins++
        val plVsPlStatLoser =
            playerVsPlayerStatisticsRepository.getByPlayersNames(loser, winner)
        plVsPlStatLoser.loses++
        playerVsPlayerStatisticsRepository.updateStatistics(plVsPlStatWinner)
        playerVsPlayerStatisticsRepository.updateStatistics(plVsPlStatLoser)
    }

    private fun updateRaceVsRaceDatabase(winner:String, loser:String){
        if ( raceVsRaceStatisticsRepository.getByRacesNames(winner, loser) == null) {
            raceVsRaceStatisticsRepository.insert(
                RaceVsRaceStatistics(0, winner, loser, 0, 0)
            )
            raceVsRaceStatisticsRepository.insert(
                RaceVsRaceStatistics(0, loser, winner, 0, 0)
            )
        }
        val statWinner =
            raceVsRaceStatisticsRepository.getByRacesNames(winner, loser)
       statWinner.wins++
        val statLoser =
            raceVsRaceStatisticsRepository.getByRacesNames(loser, winner)
       statLoser.loses++
        raceVsRaceStatisticsRepository.updateStatistics(statWinner)
        raceVsRaceStatisticsRepository.updateStatistics(statLoser)
    }
}