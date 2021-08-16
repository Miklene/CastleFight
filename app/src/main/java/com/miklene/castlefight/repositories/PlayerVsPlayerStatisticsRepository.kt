package com.miklene.castlefight.repositories

import android.app.Application
import androidx.room.Insert
import com.miklene.castlefight.model.PlayerVsPlayerStatistics
import com.miklene.castlefight.room.statistics.PlayerVsPlayerStatisticsDao
import com.miklene.castlefight.room.statistics.PlayerVsPlayerStatisticsDatabase

class PlayerVsPlayerStatisticsRepository(val application: Application) {
    private val playerVsPlayerStatisticsDao: PlayerVsPlayerStatisticsDao

    init {
        val playerVsPlayerStatisticsDatabase =
            PlayerVsPlayerStatisticsDatabase.getPlayerVsPlayerStatisticsDatabase(application)!!
        playerVsPlayerStatisticsDao = playerVsPlayerStatisticsDatabase.playerVsPlayerStatisticsDao()
    }

    fun insert(playerVsPlayerStatistics: PlayerVsPlayerStatistics) {
        playerVsPlayerStatisticsDao.insert(playerVsPlayerStatistics)
    }

    fun getByPlayersNames(owner: String, enemy: String): PlayerVsPlayerStatistics {
        return playerVsPlayerStatisticsDao.getByPlayersNames(owner, enemy)
    }

    fun updateStatistics(playerVsPlayerStatistics: PlayerVsPlayerStatistics){
        playerVsPlayerStatisticsDao.updateStatistics(playerVsPlayerStatistics)
    }

    fun getByOwnerName(owner: String): List<PlayerVsPlayerStatistics>{
        return playerVsPlayerStatisticsDao.getByOwnerName(owner)
    }
}