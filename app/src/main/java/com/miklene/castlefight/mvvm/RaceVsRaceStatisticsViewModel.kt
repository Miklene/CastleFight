package com.miklene.castlefight.mvvm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.PlayerVsPlayerStatistics
import com.miklene.castlefight.model.RaceVsRaceStatistics
import com.miklene.castlefight.repositories.PlayerVsPlayerStatisticsRepository
import com.miklene.castlefight.repositories.RaceVsRaceStatisticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class RaceVsRaceStatisticsViewModel(@NonNull application: Application) :
    AndroidViewModel(application) {

    private val raceVsRaceStatisticsRepository: RaceVsRaceStatisticsRepository
    private lateinit var callback: RaceVsRaceStatisticsCallback

    init {
        raceVsRaceStatisticsRepository = RaceVsRaceStatisticsRepository(application)
    }

    interface RaceVsRaceStatisticsCallback {
        fun getByRaceNamesCallback(raceVsRaceStatistics: RaceVsRaceStatistics)
        fun getByOwnerRaceNameCallback(raceVsRaceStatistics: List<RaceVsRaceStatistics>)
    }

    fun attachCallback(raceVsRaceStatisticsCallback: RaceVsRaceStatisticsCallback) {
        callback = raceVsRaceStatisticsCallback;
    }

    fun insert(raceVsRaceStatistics: RaceVsRaceStatistics) {
        viewModelScope.launch(Dispatchers.IO) {
            raceVsRaceStatisticsRepository.insert(raceVsRaceStatistics)
        }
    }

    fun getByRacesNames(owner: String, enemy: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val statistics = raceVsRaceStatisticsRepository.getByRacesNames(owner, enemy)
            try {
                callback.getByRaceNamesCallback(statistics)
            } catch (e: NullPointerException) {
                Log.d("Callback", "PlayerVsPlayerStatisticsViewModel not attached callback")
            }
        }
    }

    fun updateStatistics(raceVsRaceStatistics: RaceVsRaceStatistics) {
        viewModelScope.launch(Dispatchers.IO) {
            raceVsRaceStatisticsRepository.updateStatistics(raceVsRaceStatistics)
        }
    }

    fun getByOwnerName(owner: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val statistics = raceVsRaceStatisticsRepository.getByOwnerName(owner)
            try {
                callback.getByOwnerRaceNameCallback(statistics)
            } catch (e: NullPointerException) {
                Log.d("Callback", "PlayerVsPlayerStatisticsViewModel not attached callback")
            }
        }
    }
}