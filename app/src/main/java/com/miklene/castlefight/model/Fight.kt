package com.miklene.castlefight.model

import androidx.room.*
import com.miklene.castlefight.room.BanConverter
import com.miklene.castlefight.room.RoundConverter

@Entity
data class Fight(@PrimaryKey(autoGenerate = true)val fightId:Long, val year:Int, val firstPlayer:String, val secondPlayer:String) {

    
}