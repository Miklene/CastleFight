package com.miklene.castlefight.room.statistics

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklene.castlefight.model.PlayerStatisticsByRace

@Database(entities = [PlayerStatisticsByRace::class], version = 1)
abstract class PlayerStatisticsByRaceDatabase:RoomDatabase() {
    abstract fun playerStatisticsByRaceDao():PlayerStatisticsByRaceDao

    companion object {
        var INSTANCE: PlayerStatisticsByRaceDatabase? = null

        fun getPlayerStatisticByRaceDatabase(context: Context): PlayerStatisticsByRaceDatabase? {
            if (INSTANCE == null) {
                synchronized(PlayerStatisticsByRace::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PlayerStatisticsByRaceDatabase::class.java,
                        "PlayerStatisticByRace"
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