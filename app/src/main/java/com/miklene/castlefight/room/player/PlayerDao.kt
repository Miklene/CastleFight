package com.miklene.castlefight.room.player

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miklene.castlefight.model.Player

@Dao
interface PlayerDao {

    @Insert
    suspend fun insert(player: Player)

    @Update
    suspend fun update(player: Player)

    @Delete
    suspend fun delete(player: Player)

    @Query("SELECT * FROM player WHERE name == :name")
    suspend fun getByName(name: String): Player

    @Query("SELECT * FROM player")
    fun getAll(): LiveData<List<Player>>

    @Query("DELETE FROM player")
    suspend fun deleteAll()

    @Query("UPDATE player SET wins = :wins WHERE name = :playerName")
    suspend fun updatePlayerWins(playerName: String, wins: Int)

    @Query("UPDATE player SET loses = :loses WHERE name = :playerName")
    suspend fun updatePlayerLoses(playerName: String, loses: Int)
}