package com.miklene.castlefight.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race
import com.miklene.castlefight.room.race.RaceDao
import com.miklene.castlefight.room.race.RaceDatabase

class RaceRepository(application: Application) {
    private val raceDao: RaceDao
    private var allRaces: LiveData<List<Race>>

    init {
        val raceDatabase: RaceDatabase = RaceDatabase.getRaceDatabase(application)!!
        raceDao = raceDatabase.raceDao()
        allRaces = raceDao.getAll()
    }

    fun getAllRaces(): LiveData<List<Race>> {
        return allRaces
    }

    suspend fun getRaceByName(name: String): Race {
        return raceDao.getByName(name)
    }

    suspend fun updateRaceWins(raceName: String, wins: Int) {
        raceDao.updateRaceWins(raceName, wins)
    }

    suspend fun updateRaceLoses(raceName: String, loses: Int) {
        raceDao.updateRaceLoses(raceName, loses)
    }
}