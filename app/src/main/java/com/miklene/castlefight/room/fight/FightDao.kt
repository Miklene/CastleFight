package com.miklene.castlefight.room.fight

import androidx.room.*
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.model.FightWithBansAndRounds

@Dao
interface FightDao {

    @Insert
    fun insert(fight: Fight): Long

    @Update
    suspend fun update(fight: Fight)

    @Delete
    suspend fun delete(fight: Fight)

    @Transaction
    @Query("SELECT * FROM fight")
    fun getFight(): List<FightWithBansAndRounds>
}