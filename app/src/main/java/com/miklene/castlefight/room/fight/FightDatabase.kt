package com.miklene.castlefight.room.fight

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race

@Database(entities = [Fight::class, Player::class, Race::class], version = 1)
abstract class FightDatabase : RoomDatabase() {
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

        fun destroyDatabase(){
            INSTANCE = null
        }
    }
}