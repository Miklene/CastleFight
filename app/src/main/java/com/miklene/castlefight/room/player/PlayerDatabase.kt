package com.miklene.castlefight.room.player

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.miklene.castlefight.model.Player
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Player::class], version = 1)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao

    companion object {
        var INSTANCE: PlayerDatabase? = null
        fun getPlayerDatabase(context: Context): PlayerDatabase? {
            if (INSTANCE == null) {
                synchronized(PlayerDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PlayerDatabase::class.java,
                        "PlayerDatabase"
                    )
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                val dao = INSTANCE?.playerDao()
                               CoroutineScope(Dispatchers.IO).launch {
                                    val players: Array<String> =
                                        arrayOf("Антон", "Витя", "Даня", "Миша", "Никита", "Кирюха")
                                    for (player in players)
                                        dao?.insert(Player(player))
                                }
                            }

                        })
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}