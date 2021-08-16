package com.miklene.castlefight.room.statistics

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklene.castlefight.model.PlayerVsPlayerStatistics


@Database(entities = [PlayerVsPlayerStatistics::class], version = 1)
abstract class PlayerVsPlayerStatisticsDatabase : RoomDatabase() {
    abstract fun playerVsPlayerStatisticsDao(): PlayerVsPlayerStatisticsDao

    companion object {
        var INSTANCE: PlayerVsPlayerStatisticsDatabase? = null

        fun getPlayerVsPlayerStatisticsDatabase(context: Context): PlayerVsPlayerStatisticsDatabase? {
            if (INSTANCE == null) {
                synchronized(PlayerVsPlayerStatistics::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PlayerVsPlayerStatisticsDatabase::class.java,
                        "PlayerVsPlayerStatistics"
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