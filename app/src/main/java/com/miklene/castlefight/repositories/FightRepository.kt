package com.miklene.castlefight.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.model.FightWithBansAndRounds
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.room.fight.FightDao
import com.miklene.castlefight.room.fight.FightDatabase
import com.miklene.castlefight.room.player.PlayerDao
import com.miklene.castlefight.room.player.PlayerDatabase

class FightRepository(val application: Application) {
    private val fightDao: FightDao
    //private var allFights: LiveData<List<Fight>>

    init {
        val fightDatabase: FightDatabase = FightDatabase.getFightDatabase(application)!!
        fightDao = fightDatabase.fightDao()
        //allPlayers = playerDao.getAll()
    }

    fun insert(fight: Fight):Long{
        return fightDao.insert(fight)
    }

    fun getFight(): List<FightWithBansAndRounds>{
        return fightDao.getFight()
    }
}