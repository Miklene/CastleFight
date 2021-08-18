package com.miklene.castlefight.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
   /*
        ForeignKey(
            entity = Player::class,
            parentColumns = ["name"],
            childColumns = ["loser"]
        ),
        ForeignKey(
            entity = Race::class,
            parentColumns = ["name"],
            childColumns = ["winnerRace"]
        ),
        ForeignKey(
            entity = Race::class,
            parentColumns = ["name"],
            childColumns =["loserRace"]
        )*/

)

data class Round(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val winner: String,
    val loser: String,
    val winnerRace: String,
    val loserRace: String
) {

}