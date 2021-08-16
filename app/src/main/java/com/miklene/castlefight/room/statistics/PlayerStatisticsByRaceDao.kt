package com.miklene.castlefight.room.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miklene.castlefight.model.PlayerStatisticByRace
import com.miklene.castlefight.model.PlayerVsPlayerStatistics

@Dao
interface PlayerStatisticsByRaceDao {

    @Insert
    fun insert(playerStatisticByRace: PlayerStatisticByRace)

    @Query("SELECT * FROM playerstatisticbyrace WHERE playerName == :playerName AND raceName==:raceName")
    fun getByPlayerAndRaceNames(playerName: String, raceName: String): PlayerStatisticByRace

    @Update
    fun updateStatistics(playerStatisticByRace: PlayerStatisticByRace)
}