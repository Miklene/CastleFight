package com.miklene.castlefight.room.race

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.miklene.castlefight.model.Race
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Race::class], version = 1)
abstract class RaceDatabase : RoomDatabase() {
    abstract fun raceDao(): RaceDao


    companion object {
        var INSTANCE: RaceDatabase? = null
        fun getRaceDatabase(context: Context): RaceDatabase? {
            if (INSTANCE == null) {
                synchronized(RaceDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RaceDatabase::class.java,
                        "RaceDatabase"
                    )
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                val dao = INSTANCE?.raceDao()
                                CoroutineScope(Dispatchers.IO).launch {
                                    val race: Array<String> = arrayOf(
                                        "Люди", "Наги", "Нежить", "Ночные эльфы", "Орки",
                                        "Порча", "Светлые эльфы", "Северные", "Техники", "Хаос"
                                    )
                                    for (r in race)
                                        dao?.insert(Race(r))
                                }
                            }

                        }).build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}