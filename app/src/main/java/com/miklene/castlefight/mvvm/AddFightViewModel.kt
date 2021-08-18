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
    private val roundRepository: RoundRepository
    private val playerVsPlayerStatisticsRepository: PlayerVsPlayerStatisticsRepository
    private val raceVsRaceStatisticsRepository: RaceVsRaceStatisticsRepository
    private val playerStatisticsByRaceRepository: PlayerStatisticsByRaceRepository
    private lateinit var playerLiveData: LiveData<List<Player>>
    private lateinit var raceLiveData: LiveData<List<Race>>
    private lateinit var callback: AddFightViewModelCallback


    interface AddFightViewModelCallback{
        fun insetComplete()
    }

    fun attachCallback(addFightViewModelCallback: AddFightViewModelCallback){
        callback = addFightViewModelCallback
    }

    init {
        playerRepsitory = PlayerRepository(application)
        raceRepository = RaceRepository(application)
        roundRepository = RoundRepository(application)
        playerVsPlayerStatisticsRepository = PlayerVsPlayerStatisticsRepository(application)
        raceVsRaceStatisticsRepository = RaceVsRaceStatisticsRepository(application)
        playerStatisticsByRaceRepository = PlayerStatisticsByRaceRepository(application)
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
    fun insertFights(rounds: List<Round>) {
        CoroutineScope(Dispatchers.IO).launch {
            for(fight in rounds) {
                roundRepository.insert(fight)
                val playerWins = roundRepository.getPlayerWins(fight.winner).size
                val playerLoses = roundRepository.getPlayerLoses(fight.loser).size
                val raceWins = roundRepository.getRaceWins(fight.winnerRace).size
                val raceLoses = roundRepository.getRaceLoses(fight.loserRace).size
                playerRepsitory.updatePlayerWins(fight.winner, playerWins)
                playerRepsitory.updatePlayerLoses(fight.loser, playerLoses)
                raceRepository.updateRaceWins(fight.winnerRace, raceWins)
                raceRepository.updateRaceLoses(fight.loserRace, raceLoses)
                updatePlVsPlDatabase(fight.winner, fight.loser)
                updateRaceVsRaceDatabase(fight.winnerRace, fight.loserRace)
                updatePlayerStatisticsByRace(fight)
            }
            callback.insetComplete()
        }
    }

    fun insertFight(round: Round) {
        CoroutineScope(Dispatchers.IO).launch {
            roundRepository.insert(round)
            val playerWins = roundRepository.getPlayerWins(round.winner).size
            val playerLoses = roundRepository.getPlayerLoses(round.loser).size
            val raceWins = roundRepository.getRaceWins(round.winnerRace).size
            val raceLoses = roundRepository.getRaceLoses(round.loserRace).size
            playerRepsitory.updatePlayerWins(round.winner, playerWins)
            playerRepsitory.updatePlayerLoses(round.loser, playerLoses)
            raceRepository.updateRaceWins(round.winnerRace, raceWins)
            raceRepository.updateRaceLoses(round.loserRace, raceLoses)
            updatePlVsPlDatabase(round.winner, round.loser)
            updateRaceVsRaceDatabase(round.winnerRace, round.loserRace)
            updatePlayerStatisticsByRace(round)
        }
    }

    private fun updatePlVsPlDatabase(winner: String, loser: String) {
        if (playerVsPlayerStatisticsRepository.getByPlayersNames(winner, loser) == null) {
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

    private fun updateRaceVsRaceDatabase(winner: String, loser: String) {
        if (raceVsRaceStatisticsRepository.getByRacesNames(winner, loser) == null) {
            raceVsRaceStatisticsRepository.insert(
                RaceVsRaceStatistics(0, winner, loser, 0, 0)
            )
        }
        val statWinner =
            raceVsRaceStatisticsRepository.getByRacesNames(winner, loser)
        statWinner.wins++
        if (winner == loser)
            statWinner.loses++
        raceVsRaceStatisticsRepository.updateStatistics(statWinner)
        if (winner != loser) {
            if (raceVsRaceStatisticsRepository.getByRacesNames(loser, winner) == null) {
                raceVsRaceStatisticsRepository.insert(
                    RaceVsRaceStatistics(0, loser, winner, 0, 0)
                )
            }
            val statLoser =
                raceVsRaceStatisticsRepository.getByRacesNames(loser, winner)
            statLoser.loses++
            raceVsRaceStatisticsRepository.updateStatistics(statLoser)
        }
    }

    private fun updatePlayerStatisticsByRace(round: Round) {
        if (playerStatisticsByRaceRepository
                .getByPlayerAndRaceNames(round.winner, round.winnerRace) == null
        ) {
            playerStatisticsByRaceRepository.insert(
                PlayerStatisticsByRace(0, round.winner, round.winnerRace, 0, 0)
            )
        }
        val pSBRWinner =
            playerStatisticsByRaceRepository.getByPlayerAndRaceNames(round.winner, round.winnerRace)
        pSBRWinner.wins++
        playerStatisticsByRaceRepository.updateStatistics(pSBRWinner)
        if (playerStatisticsByRaceRepository.getByPlayerAndRaceNames(
                round.loser,
                round.loserRace
            ) == null
        ) {
            playerStatisticsByRaceRepository.insert(
                PlayerStatisticsByRace(0, round.loser, round.loserRace, 0, 0)
            )
        }
        val pSBRLoser =
            playerStatisticsByRaceRepository.getByPlayerAndRaceNames(round.loser, round.loserRace)
        pSBRLoser.loses++
        playerStatisticsByRaceRepository.updateStatistics(pSBRLoser)
    }
}