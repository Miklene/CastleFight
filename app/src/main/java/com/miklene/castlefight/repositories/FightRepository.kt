package com.miklene.castlefight.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.miklene.castlefight.R
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.room.fight.FightDao
import com.miklene.castlefight.room.fight.FightDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FightRepository(val application: Application) {
    private val fightDao: FightDao
    //private var allFights: LiveData<List<Fight>>
    private val executor: Executor = Executors.newSingleThreadExecutor()

    init {
        val fightDatabase: FightDatabase = FightDatabase.getFightDatabase(application)!!
        fightDao = fightDatabase.fightDao()
    }

    suspend fun getAllFights(): LiveData<List<Fight>> {
        return fightDao.getAll()
    }

    suspend fun getFight(id: Int): Fight {
        return fightDao.getById(id)
    }

    suspend fun insert(fight: Fight) {
        fightDao.insert(fight)
    }

    suspend fun deleteFight(fight: Fight){
        fightDao.delete(fight)
    }

    suspend fun deleteAll()  {
            fightDao.deleteAll()
    }

    suspend fun getPlayerWins(playerName: String): List<Fight>{
        return fightDao.getPlayerWins(playerName)
    }

    suspend fun getPlayerLoses( playerName: String): List<Fight>{
        return fightDao.getPlayerLoses(playerName)
    }

    suspend fun getRaceWins(raceName: String): List<Fight>{
        return fightDao.getRaceWins(raceName)
    }

    suspend fun getRaceLoses(raceName: String): List<Fight>{
        return fightDao.getRaceLoses(raceName)
    }

    suspend fun getPlayerWinsUnderAnotherPlayer(winnerName: String, loserName:String): List<Fight>{
        return fightDao.getPlayerWinsUnderAnotherPlayer(winnerName,loserName)
    }

    suspend fun getRaceWinsUnderAnotherRace(winnerName: String, loserName: String): List<Fight>{
        return fightDao.getRaceWinsUnderAnotherRace(winnerName,loserName)
    }

    suspend fun getPlayerRaceWins(raceName: String, playerName: String): List<Fight>{
        return fightDao.getPlayerRaceWins(raceName, playerName)
    }

    suspend fun getPlayerRaceLoses(raceName: String, playerName: String): List<Fight>{
        return fightDao.getPlayerRaceLoses(raceName, playerName)
    }
}