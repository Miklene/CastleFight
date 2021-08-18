package com.miklene.castlefight.room.fight

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklene.castlefight.model.Round
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race

@Database(entities = [Round::class, Player::class, Race::class], version = 1)
abstract class RoundDatabase : RoomDatabase() {
    abstract fun fightDao(): RoundDao

    companion object {
        var INSTANCE: RoundDatabase? = null
        fun getFightDatabase(context: Context): RoundDatabase? {
            if (INSTANCE == null) {
                synchronized(RoundDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoundDatabase::class.java,
                        "FightDatabase"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase(){
            INSTANCE = null
        }
    }
}