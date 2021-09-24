package com.miklene.castlefight.model

import androidx.room.Embedded
import androidx.room.Relation

data class FightWithBansAndRounds (
    @Embedded
    var fight: Fight,
    @Relation(parentColumn = "fightId", entityColumn = "roundFightId")
    var rounds:List<Round>)
    /*@Relation(parentColumn = "fight_id", entityColumn = "fightId")
    var bans:List<Ban>)*/{
}