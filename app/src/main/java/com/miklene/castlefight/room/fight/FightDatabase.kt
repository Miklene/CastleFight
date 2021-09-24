package com.miklene.castlefight.room.fight

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklene.castlefight.model.Ban
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.model.Round


@Database(entities = [Fight::class, Round::class, Ban::class], version = 1)
abstract class FightDatabase:RoomDatabase() {
    abstract fun fightDao(): FightDao


    companion object {
        var INSTANCE: FightDatabase? = null
        fun getFightDatabase(context: Context): FightDatabase? {
            if (INSTANCE == null) {
                synchronized(FightDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FightDatabase::class.java,
                        "FightDatabase"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}