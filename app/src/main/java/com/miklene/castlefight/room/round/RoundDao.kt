package com.miklene.castlefight.room.round

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miklene.castlefight.model.Round

@Dao
interface RoundDao {
    @Insert
    suspend fun insert(round: Round)

    @Update
    suspend fun update(round: Round)

    @Delete
    suspend fun delete(round: Round)

    @Query("SELECT * FROM round WHERE id == :id")
    suspend fun getById(id: Int): Round

    @Query("SELECT * FROM round")
    fun getAll(): LiveData<List<Round>>

    @Query("DELETE FROM round")
    suspend fun deleteAll()

    @Query("SELECT * FROM round WHERE winner == :playerName")
    suspend fun getPlayerWins(playerName: String): List<Round>

    @Query("SELECT * FROM round WHERE loser == :playerName")
    suspend fun getPlayerLoses(playerName: String): List<Round>

    @Query("SELECT * FROM round WHERE winnerRace == :raceName")
    suspend fun getRaceWins(raceName: String): List<Round>

    @Query("SELECT * FROM round WHERE loserRace == :raceName")
    suspend fun getRaceLoses(raceName: String): List<Round>

    @Query("SELECT * FROM round WHERE winner == :winnerName AND loser == :loserName")
    suspend fun getPlayerWinsUnderAnotherPlayer(winnerName: String, loserName: String): List<Round>

    @Query("SELECT * FROM round WHERE winnerRace == :winnerName AND loserRace == :loserName")
    suspend fun getRaceWinsUnderAnotherRace(winnerName: String, loserName: String): List<Round>

    @Query("SELECT * FROM round WHERE winnerRace == :raceName AND winner == :playerName")
    suspend fun getPlayerRaceWins(raceName: String, playerName: String): List<Round>

    @Query("SELECT * FROM round WHERE loserRace == :raceName AND loser == :playerName")
    suspend fun getPlayerRaceLoses(raceName: String, playerName: String): List<Round>

}