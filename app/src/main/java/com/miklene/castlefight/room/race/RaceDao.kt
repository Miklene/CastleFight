package com.miklene.castlefight.room.race

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race

@Dao
interface RaceDao {

    @Insert
    fun insert (race: Race)

    @Update
    fun update(race: Race)

    @Delete
    fun delete(race: Race)

    @Query("SELECT * FROM race WHERE name == :name")
    suspend fun getByName(name: String): Race

    @Query("SELECT * FROM race")
    fun getAll(): LiveData<List<Race>>

    @Query("DELETE FROM race")
    fun deleteAll()

    @Query("UPDATE race SET wins = :wins WHERE name = :raceName")
    suspend fun updateRaceWins(raceName: String, wins: Int)

    @Query("UPDATE race SET loses = :loses WHERE name = :raceName")
    suspend fun updateRaceLoses(raceName: String, loses: Int)
}