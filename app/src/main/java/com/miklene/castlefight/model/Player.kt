package com.miklene.castlefight.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Player")
data class Player(@PrimaryKey() val name: String) {
    @ColumnInfo(name = "wins")
     var wins: Int = 0
    @ColumnInfo(name = "loses")
     var loses: Int = 0
}