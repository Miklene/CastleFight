package com.miklene.castlefight.room.statistics

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklene.castlefight.model.RaceVsRaceStatistics

@Database(entities = [RaceVsRaceStatistics::class], version = 1)
abstract class RaceVsRaceStatisticsDatabase : RoomDatabase() {
    abstract fun raceVsRaceStatisticsDao(): RaceVsRaceStatisticsDao

    companion object {
        var INSTANCE: RaceVsRaceStatisticsDatabase? = null

        fun getRaceVsRaceStatisticsDatabase(context: Context): RaceVsRaceStatisticsDatabase? {
            if (INSTANCE == null) {
                synchronized(RaceVsRaceStatistics::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RaceVsRaceStatisticsDatabase::class.java,
                        "RaceVsRaceStatistics"
                    ).build()
                    /*.addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val dao = INSTANCE?.PlayerVsPlayerStatisticsDao()
                            CoroutineScope(Dispatchers.IO).launch {
                                val race: Array<String> = arrayOf(
                                    "Люди", "Наги", "Нежить", "Ночные эльфы", "Орки",
                                    "Порча", "Светлые эльфы", "Северные", "Техники", "Хаос"
                                )
                                for (r in race)
                                    dao?.insert(Race(r))
                            }
                        }

                    })*/
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}