package com.miklene.castlefight.mvvm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.Round
import com.miklene.castlefight.repositories.RoundRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class RoundViewModel(@NonNull application: Application) : AndroidViewModel(application) {
    private val roundRepository: RoundRepository
    private lateinit var callback: FightCallback
    private lateinit var roundLiveData: LiveData<List<Round>>

    init {
        roundRepository = RoundRepository(application)
        viewModelScope.launch {
            roundLiveData = roundRepository.getAllRounds()
        }
    }

    fun attachPlayerCallBack(_callback: FightCallback) {
        callback = _callback
    }

    interface FightCallback {
        suspend fun updatePlayerWinsStat(wins: List<Round>, loses:List<Round>, loserName: String)
        suspend fun updateRaceWinStat(wins: List<Round>, loses:List<Round>, loserName: String)
        suspend fun updatePlayersRaceStat(wins: List<Round>, loses:List<Round>, raceName: String)
    }

    fun getAllFights(): LiveData<List<Round>> {
        return roundLiveData
    }

    fun getPlayerWinsAndLosesUnderAnotherPlayer(winnerName: String, loserName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val wins = roundRepository.getPlayerWinsUnderAnotherPlayer(winnerName,loserName)
            val loses = roundRepository.getPlayerWinsUnderAnotherPlayer(loserName,winnerName)
            try {
                callback.updatePlayerWinsStat(wins, loses, loserName)
            } catch (e: NullPointerException){
                Log.d("Callback", "FightViewModel not attached callback")
            }
        }
    }


    fun getRaceWinsAndLosesUnderAnotherRace(winnerName: String, loserName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val wins = roundRepository.getRaceWinsUnderAnotherRace(winnerName,loserName)
            val loses = roundRepository.getRaceWinsUnderAnotherRace(loserName,winnerName)
            try {
                callback.updateRaceWinStat(wins, loses, loserName)
            } catch (e: NullPointerException){
                Log.d("Callback", "FightViewModel not attached callback")
            }
        }
    }

    fun getPlayerRaceWinsAndLoses(raceName: String, playerName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val wins = roundRepository.getPlayerRaceWins(raceName,playerName)
            val loses = roundRepository.getPlayerRaceLoses(raceName,playerName)
            try {
                callback.updatePlayersRaceStat(wins, loses, raceName)
            } catch (e: NullPointerException){
                Log.d("Callback", "FightViewModel not attached callback")
            }
        }
    }

    fun insertFight(round: Round) {
        CoroutineScope(Dispatchers.IO).launch {
            roundRepository.insert(round)
        }
    }

    fun deleteFight(round: Round) {
        CoroutineScope(Dispatchers.IO).launch {
            roundRepository.deleteRound(round)
        }
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            roundRepository.deleteAll()
        }
    }

}