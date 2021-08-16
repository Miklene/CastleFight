package com.miklene.castlefight.room.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miklene.castlefight.model.PlayerVsPlayerStatistics
import com.miklene.castlefight.model.RaceVsRaceStatistics

@Dao
interface RaceVsRaceStatisticsDao {
    @Insert
    fun insert(raceVsRaceStatistics: RaceVsRaceStatistics)

    @Query("SELECT * FROM racevsracestatistics WHERE ownerName == :owner AND enemyName==:enemy")
    fun getByRacesNames(owner: String, enemy: String): RaceVsRaceStatistics

    @Update
    fun updateStatistics(raceVsRaceStatistics: RaceVsRaceStatistics)

    @Query("SELECT * FROM racevsracestatistics WHERE ownerName = :owner")
    fun getByOwnerName(owner: String): List<RaceVsRaceStatistics>
}