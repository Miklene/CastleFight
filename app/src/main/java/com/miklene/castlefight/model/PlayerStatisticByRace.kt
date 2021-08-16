package com.miklene.castlefight.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayerStatisticByRace(
    @PrimaryKey
    private val id:Int,
    private val playerName: String,
    private val raceName: String,
    private val wins: Int,
    private val loses: Int
) {
   val fights: Int
        get() {
            return wins + loses
        }

    val winRate: Int
        get() {
            if (fights != 0)
                return ((wins.toDouble() / (wins + loses).toDouble()) * 100).toInt()
            return 0
        }
}