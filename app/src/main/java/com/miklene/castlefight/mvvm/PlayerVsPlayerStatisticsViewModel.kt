package com.miklene.castlefight.mvvm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.PlayerVsPlayerStatistics
import com.miklene.castlefight.repositories.PlayerVsPlayerStatisticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class PlayerVsPlayerStatisticsViewModel(@NonNull application: Application) :
    AndroidViewModel(application) {

    private val playerVsPlayerStatisticsRepository: PlayerVsPlayerStatisticsRepository
    private lateinit var callback: PlayerVsPlayerStatisticsCallback

    init {
        playerVsPlayerStatisticsRepository = PlayerVsPlayerStatisticsRepository(application)
    }

    interface PlayerVsPlayerStatisticsCallback{
        fun getByPlayersNamesCallback(playerVsPlayerStatistics: PlayerVsPlayerStatistics)
        fun getByOwnerPlayerNameCallback(playerVsPlayerStatistics: List<PlayerVsPlayerStatistics>)
    }

    fun attachCallback(playerVsPlayerStatisticsCallback: PlayerVsPlayerStatisticsCallback){
        callback = playerVsPlayerStatisticsCallback;
    }

    fun insert(playerVsPlayerStatistics: PlayerVsPlayerStatistics) {
        viewModelScope.launch(Dispatchers.IO) {
            playerVsPlayerStatisticsRepository.insert(playerVsPlayerStatistics)
        }
    }

    fun getByPlayersNames(owner: String, enemy: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val statistics = playerVsPlayerStatisticsRepository.getByPlayersNames(owner, enemy)
            try {
                callback.getByPlayersNamesCallback(statistics)
            } catch (e: NullPointerException){
                Log.d("Callback", "PlayerVsPlayerStatisticsViewModel not attached callback")
            }
        }
    }

    fun getByOwnerName(owner: String){
        viewModelScope.launch(Dispatchers.IO) {
            val statistics = playerVsPlayerStatisticsRepository.getByOwnerName(owner)
            try {
                callback.getByOwnerPlayerNameCallback(statistics)
            } catch (e: NullPointerException){
                Log.d("Callback", "PlayerVsPlayerStatisticsViewModel not attached callback")
            }
        }
    }

    fun updateStatistics(playerVsPlayerStatistics: PlayerVsPlayerStatistics) {
        viewModelScope.launch(Dispatchers.IO) {
            playerVsPlayerStatisticsRepository.updateStatistics(playerVsPlayerStatistics)
        }
    }
}