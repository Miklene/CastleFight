package com.miklene.castlefight.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.room.player.PlayerDao
import com.miklene.castlefight.room.player.PlayerDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class PlayerRepository(val application: Application) {
    private val playerDao: PlayerDao
    private var allPlayers: LiveData<List<Player>>

    init {
        val playerDatabase: PlayerDatabase = PlayerDatabase.getPlayerDatabase(application)!!
        playerDao = playerDatabase.playerDao()
        allPlayers = playerDao.getAll()
    }

    fun getAllPlayers():LiveData<List<Player>>{
        return playerDao.getAll()
    }

    suspend fun getPlayerByName(name:String):Player{
        return playerDao.getByName(name)
    }

    suspend fun updatePlayerWins(playerName: String, wins: Int){
        playerDao.updatePlayerWins(playerName, wins)
    }

    suspend fun updatePlayerLoses(playerName: String, loses: Int){
        playerDao.updatePlayerLoses(playerName, loses)
    }
}