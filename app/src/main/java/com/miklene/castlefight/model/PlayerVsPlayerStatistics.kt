package com.miklene.castlefight.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayerVsPlayerStatistics(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ownerName: String,
    val enemyName: String,
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