package com.miklene.castlefight.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayerStatisticByRace(
    @PrimaryKey
    val id:Int,
    val playerName: String,
    val raceName: String,
    var wins: Int,
    var loses: Int
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