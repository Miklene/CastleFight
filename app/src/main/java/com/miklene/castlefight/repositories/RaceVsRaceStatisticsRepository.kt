package com.miklene.castlefight.repositories

import android.app.Application
import com.miklene.castlefight.model.PlayerVsPlayerStatistics
import com.miklene.castlefight.model.RaceVsRaceStatistics
import com.miklene.castlefight.room.statistics.PlayerVsPlayerStatisticsDao
import com.miklene.castlefight.room.statistics.PlayerVsPlayerStatisticsDatabase
import com.miklene.castlefight.room.statistics.RaceVsRaceStatisticsDao
import com.miklene.castlefight.room.statistics.RaceVsRaceStatisticsDatabase

class RaceVsRaceStatisticsRepository(val application: Application) {
    private val raceVsRaceStatisticsDao: RaceVsRaceStatisticsDao

    init {
        val raceVsRaceStatisticsDatabase =
            RaceVsRaceStatisticsDatabase.getRaceVsRaceStatisticsDatabase(application)!!
        raceVsRaceStatisticsDao = raceVsRaceStatisticsDatabase.raceVsRaceStatisticsDao()
    }

    fun insert(raceVsRaceStatistics: RaceVsRaceStatistics) {
        raceVsRaceStatisticsDao.insert(raceVsRaceStatistics)
    }

    fun getByRacesNames(owner: String, enemy: String): RaceVsRaceStatistics {
        return raceVsRaceStatisticsDao.getByRacesNames(owner, enemy)
    }

    fun updateStatistics(raceVsRaceStatistics: RaceVsRaceStatistics){
        raceVsRaceStatisticsDao.updateStatistics(raceVsRaceStatistics)
    }

    fun getByOwnerName(owner: String): List<RaceVsRaceStatistics>{
        return raceVsRaceStatisticsDao.getByOwnerName(owner)
    }
}