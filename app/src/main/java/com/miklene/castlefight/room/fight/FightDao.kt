package com.miklene.castlefight.room.fight

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miklene.castlefight.model.Fight

@Dao
interface FightDao {
    @Insert
    suspend fun insert(fight: Fight)

    @Update
    suspend fun update(fight: Fight)

    @Delete
    suspend fun delete(fight: Fight)

    @Query("SELECT * FROM fight WHERE id == :id")
    suspend fun getById(id: Int): Fight

    @Query("SELECT * FROM fight")
    fun getAll(): LiveData<List<Fight>>

    @Query("DELETE FROM fight")
    suspend fun deleteAll()

    @Query("SELECT * FROM fight WHERE winner == :playerName")
    suspend fun getPlayerWins(playerName: String): List<Fight>

    @Query("SELECT * FROM fight WHERE loser == :playerName")
    suspend fun getPlayerLoses(playerName: String): List<Fight>

    @Query("SELECT * FROM fight WHERE winnerRace == :raceName")
    suspend fun getRaceWins(raceName: String): List<Fight>

    @Query("SELECT * FROM fight WHERE loserRace == :raceName")
    suspend fun getRaceLoses(raceName: String): List<Fight>

    @Query("SELECT * FROM fight WHERE winner == :winnerName AND loser == :loserName")
    suspend fun getPlayerWinsUnderAnotherPlayer(winnerName: String, loserName: String): List<Fight>

    @Query("SELECT * FROM fight WHERE winnerRace == :winnerName AND loserRace == :loserName")
    suspend fun getRaceWinsUnderAnotherRace(winnerName: String, loserName: String): List<Fight>

    @Query("SELECT * FROM fight WHERE winnerRace == :raceName AND winner == :playerName")
    suspend fun getPlayerRaceWins(raceName: String, playerName: String): List<Fight>

    @Query("SELECT * FROM fight WHERE loserRace == :raceName AND loser == :playerName")
    suspend fun getPlayerRaceLoses(raceName: String, playerName: String): List<Fight>

}