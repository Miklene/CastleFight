package com.miklene.castlefight.repositories

import android.app.Application
import com.miklene.castlefight.model.PlayerStatisticByRace
import com.miklene.castlefight.model.PlayerVsPlayerStatistics
import com.miklene.castlefight.room.statistics.PlayerStatisticsByRaceDao
import com.miklene.castlefight.room.statistics.PlayerStatisticsByRaceDatabase
import com.miklene.castlefight.room.statistics.PlayerVsPlayerStatisticsDao
import com.miklene.castlefight.room.statistics.PlayerVsPlayerStatisticsDatabase

class PlayerStatisticsByRaceRepository(val application: Application) {
    private val playerStatisticsByRaceDao: PlayerStatisticsByRaceDao

    init {
        val playerStatisticsByRaceDatabase =
            PlayerStatisticsByRaceDatabase.getPlayerStatisticByRaceDatabase(application)!!
        playerStatisticsByRaceDao = playerStatisticsByRaceDatabase.playerStatisticsByRaceDao()
    }

    fun insert(playerStatisticByRace: PlayerStatisticByRace) {
        playerStatisticsByRaceDao.insert(playerStatisticByRace)
    }

    fun getByPlayerAndRaceNames(playerName: String, raceName: String): PlayerStatisticByRace {
        return playerStatisticsByRaceDao.getByPlayerAndRaceNames(playerName, raceName)
    }

    fun updateStatistics(playerStatisticByRace: PlayerStatisticByRace) {
        playerStatisticsByRaceDao.updateStatistics(playerStatisticByRace)
    }

}