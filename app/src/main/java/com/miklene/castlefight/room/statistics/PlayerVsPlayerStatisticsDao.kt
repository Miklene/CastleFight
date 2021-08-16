package com.miklene.castlefight.room.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miklene.castlefight.model.PlayerVsPlayerStatistics

@Dao
interface PlayerVsPlayerStatisticsDao {

    @Insert
    fun insert(playerVsPlayerStatistics: PlayerVsPlayerStatistics)

    @Query("SELECT * FROM playervsplayerstatistics WHERE ownerName == :owner AND enemyName==:enemy")
    fun getByPlayersNames(owner: String, enemy: String): PlayerVsPlayerStatistics

    @Update
    fun updateStatistics(playerVsPlayerStatistics: PlayerVsPlayerStatistics)

    @Query("SELECT * FROM playervsplayerstatistics WHERE ownerName = :owner")
    fun getByOwnerName(owner: String): List<PlayerVsPlayerStatistics>
}