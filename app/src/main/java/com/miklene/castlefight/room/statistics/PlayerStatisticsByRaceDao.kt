package com.miklene.castlefight.room.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miklene.castlefight.model.PlayerStatisticsByRace

@Dao
interface PlayerStatisticsByRaceDao {

    @Insert
    fun insert(playerStatisticsByRace: PlayerStatisticsByRace)

    @Query("SELECT * FROM playerstatisticsbyrace WHERE playerName == :playerName AND raceName==:raceName")
    fun getByPlayerAndRaceNames(playerName: String, raceName: String): PlayerStatisticsByRace

    @Update
    fun updateStatistics(playerStatisticsByRace: PlayerStatisticsByRace)

    @Query("SELECT * FROM playerstatisticsbyrace WHERE playerName = :playerName")
    fun getByPlayerName(playerName: String): List<PlayerStatisticsByRace>

    @Query("SELECT * FROM playerstatisticsbyrace WHERE raceName = :raceName")
    fun getByRaceName(raceName: String): List<PlayerStatisticsByRace>
}