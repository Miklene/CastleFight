package com.miklene.castlefight.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.miklene.castlefight.model.Round
import com.miklene.castlefight.room.round.RoundDao
import com.miklene.castlefight.room.round.RoundDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RoundRepository(val application: Application) {
    private val roundDao: RoundDao
    //private var allFights: LiveData<List<Fight>>
    private val executor: Executor = Executors.newSingleThreadExecutor()

    init {
        val roundDatabase: RoundDatabase = RoundDatabase.getFightDatabase(application)!!
        roundDao = roundDatabase.fightDao()
    }

    suspend fun getAllRounds(): LiveData<List<Round>> {
        return roundDao.getAll()
    }

    suspend fun getRound(id: Int): Round {
        return roundDao.getById(id)
    }

    suspend fun insert(round: Round) {
        roundDao.insert(round)
    }

    suspend fun deleteRound(round: Round){
        roundDao.delete(round)
    }

    suspend fun deleteAll()  {
            roundDao.deleteAll()
    }

    suspend fun getPlayerWins(playerName: String): List<Round>{
        return roundDao.getPlayerWins(playerName)
    }

    suspend fun getPlayerLoses( playerName: String): List<Round>{
        return roundDao.getPlayerLoses(playerName)
    }

    suspend fun getRaceWins(raceName: String): List<Round>{
        return roundDao.getRaceWins(raceName)
    }

    suspend fun getRaceLoses(raceName: String): List<Round>{
        return roundDao.getRaceLoses(raceName)
    }

    suspend fun getPlayerWinsUnderAnotherPlayer(winnerName: String, loserName:String): List<Round>{
        return roundDao.getPlayerWinsUnderAnotherPlayer(winnerName,loserName)
    }

    suspend fun getRaceWinsUnderAnotherRace(winnerName: String, loserName: String): List<Round>{
        return roundDao.getRaceWinsUnderAnotherRace(winnerName,loserName)
    }

    suspend fun getPlayerRaceWins(raceName: String, playerName: String): List<Round>{
        return roundDao.getPlayerRaceWins(raceName, playerName)
    }

    suspend fun getPlayerRaceLoses(raceName: String, playerName: String): List<Round>{
        return roundDao.getPlayerRaceLoses(raceName, playerName)
    }
}