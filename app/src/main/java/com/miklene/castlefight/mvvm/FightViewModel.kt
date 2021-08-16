package com.miklene.castlefight.mvvm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.repositories.FightRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class FightViewModel(@NonNull application: Application) : AndroidViewModel(application) {
    private val fightRepository: FightRepository
    private lateinit var callback: FightCallback
    private lateinit var fightLiveData: LiveData<List<Fight>>

    init {
        fightRepository = FightRepository(application)
        viewModelScope.launch {
            fightLiveData = fightRepository.getAllFights()
        }
    }

    fun attachPlayerCallBack(_callback: FightCallback) {
        callback = _callback
    }

    interface FightCallback {
        suspend fun updatePlayerWinsStat(wins: List<Fight>, loses:List<Fight>, loserName: String)
        suspend fun updateRaceWinStat(wins: List<Fight>, loses:List<Fight>, loserName: String)
        suspend fun updatePlayersRaceStat(wins: List<Fight>, loses:List<Fight>, raceName: String)
    }

    fun getAllFights(): LiveData<List<Fight>> {
        return fightLiveData
    }

    fun getPlayerWinsAndLosesUnderAnotherPlayer(winnerName: String, loserName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val wins = fightRepository.getPlayerWinsUnderAnotherPlayer(winnerName,loserName)
            val loses = fightRepository.getPlayerWinsUnderAnotherPlayer(loserName,winnerName)
            try {
                callback.updatePlayerWinsStat(wins, loses, loserName)
            } catch (e: NullPointerException){
                Log.d("Callback", "FightViewModel not attached callback")
            }
        }
    }


    fun getRaceWinsAndLosesUnderAnotherRace(winnerName: String, loserName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val wins = fightRepository.getRaceWinsUnderAnotherRace(winnerName,loserName)
            val loses = fightRepository.getRaceWinsUnderAnotherRace(loserName,winnerName)
            try {
                callback.updateRaceWinStat(wins, loses, loserName)
            } catch (e: NullPointerException){
                Log.d("Callback", "FightViewModel not attached callback")
            }
        }
    }

    fun getPlayerRaceWinsAndLoses(raceName: String, playerName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val wins = fightRepository.getPlayerRaceWins(raceName,playerName)
            val loses = fightRepository.getPlayerRaceLoses(raceName,playerName)
            try {
                callback.updatePlayersRaceStat(wins, loses, raceName)
            } catch (e: NullPointerException){
                Log.d("Callback", "FightViewModel not attached callback")
            }
        }
    }

    fun insertFight(fight: Fight) {
        CoroutineScope(Dispatchers.IO).launch {
            fightRepository.insert(fight)
        }
    }

    fun deleteFight(fight: Fight) {
        CoroutineScope(Dispatchers.IO).launch {
            fightRepository.deleteFight(fight)
        }
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            fightRepository.deleteAll()
        }
    }

}