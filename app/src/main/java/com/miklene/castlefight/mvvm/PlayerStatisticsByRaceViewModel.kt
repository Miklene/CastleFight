package com.miklene.castlefight.mvvm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import androidx.room.Update
import com.miklene.castlefight.model.PlayerStatisticsByRace
import com.miklene.castlefight.model.PlayerVsPlayerStatistics
import com.miklene.castlefight.repositories.PlayerStatisticsByRaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class PlayerStatisticsByRaceViewModel(@NonNull application: Application) :
    AndroidViewModel(application) {
    private val playerStatisticsByRaceRepository: PlayerStatisticsByRaceRepository
    private lateinit var callback: PlayerStatisticsByRaceCallback

    init {
        playerStatisticsByRaceRepository = PlayerStatisticsByRaceRepository(application)
    }

    interface PlayerStatisticsByRaceCallback {
        fun getByPlayerAndRaceNamesCallback(playerStatisticsByRace: PlayerStatisticsByRace)
        fun getByPlayerNameCallback(playerStatisticsByRace: List<PlayerStatisticsByRace>)
        fun getByRaceNameCallback(playerStatisticsByRace: List<PlayerStatisticsByRace>)
    }

    fun attachCallback(playerStatisticsByRaceCallback: PlayerStatisticsByRaceCallback) {
        callback = playerStatisticsByRaceCallback;
    }

    fun insert(playerStatisticsByRace: PlayerStatisticsByRace) {
        viewModelScope.launch(Dispatchers.IO) {
            playerStatisticsByRaceRepository.insert(playerStatisticsByRace)
        }
    }

    fun getByPlayerAndRaceNames(playerName: String, raceName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val statistics =
                playerStatisticsByRaceRepository.getByPlayerAndRaceNames(playerName, raceName)
            try {
                callback.getByPlayerAndRaceNamesCallback(statistics)
            } catch (e: NullPointerException) {
                Log.d("Callback", "PlayerStatisticsByRaceViewModel not attached callback")
            }
        }
    }

    fun updateStatistics(playerStatisticsByRace: PlayerStatisticsByRace) {
        viewModelScope.launch(Dispatchers.IO) {
            playerStatisticsByRaceRepository.updateStatistics(playerStatisticsByRace)
        }
    }

    fun getByPlayerName(playerName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val statistics =
                playerStatisticsByRaceRepository.getByPlayerName(playerName)
            try {
                callback.getByPlayerNameCallback(statistics)
            } catch (e: NullPointerException) {
                Log.d("Callback", "PlayerStatisticsByRaceViewModel not attached callback")
            }
        }
    }

    fun getByRaceName(raceName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val statistics =
                playerStatisticsByRaceRepository.getByRaceName(raceName)
            try {
                callback.getByRaceNameCallback(statistics)
            } catch (e: NullPointerException) {
                Log.d("Callback", "PlayerStatisticsByRaceViewModel not attached callback")
            }
        }
    }
}