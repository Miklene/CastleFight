package com.miklene.castlefight.room.statistics

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklene.castlefight.model.PlayerStatisticByRace
import com.miklene.castlefight.model.PlayerVsPlayerStatistics

@Database(entities = [PlayerStatisticByRace::class], version = 1)
abstract class PlayerStatisticsByRaceDatabase:RoomDatabase() {
    abstract fun playerStatisticsByRaceDao():PlayerStatisticsByRaceDao

    companion object {
        var INSTANCE: PlayerStatisticsByRaceDatabase? = null

        fun getPlayerStatisticByRaceDatabase(context: Context): PlayerStatisticsByRaceDatabase? {
            if (INSTANCE == null) {
                synchronized(PlayerStatisticByRace::class) {
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