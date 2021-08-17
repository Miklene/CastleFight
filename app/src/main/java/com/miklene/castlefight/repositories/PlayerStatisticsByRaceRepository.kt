package com.miklene.castlefight.repositories

import android.app.Application
import com.miklene.castlefight.model.PlayerStatisticsByRace
import com.miklene.castlefight.room.statistics.PlayerStatisticsByRaceDao
import com.miklene.castlefight.room.statistics.PlayerStatisticsByRaceDatabase

class PlayerStatisticsByRaceRepository(val application: Application) {
    private val playerStatisticsByRaceDao: PlayerStatisticsByRaceDao

    init {
        val playerStatisticsByRaceDatabase =
            PlayerStatisticsByRaceDatabase.getPlayerStatisticByRaceDatabase(application)!!
        playerStatisticsByRaceDao = playerStatisticsByRaceDatabase.playerStatisticsByRaceDao()
    }

    fun insert(playerStatisticsByRace: PlayerStatisticsByRace) {
        playerStatisticsByRaceDao.insert(playerStatisticsByRace)
    }

    fun getByPlayerAndRaceNames(playerName: String, raceName: String): PlayerStatisticsByRace {
        return playerStatisticsByRaceDao.getByPlayerAndRaceNames(playerName, raceName)
    }

    fun updateStatistics(playerStatisticsByRace: PlayerStatisticsByRace) {
        playerStatisticsByRaceDao.updateStatistics(playerStatisticsByRace)
    }

    fun getByPlayerName(playerName: String): List<PlayerStatisticsByRace> {
        return playerStatisticsByRaceDao.getByPlayerName(playerName)
    }

    fun getByRaceName(raceName: String): List<PlayerStatisticsByRace>{
        return playerStatisticsByRaceDao.getByRaceName(raceName)
    }
}