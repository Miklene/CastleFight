package com.miklene.castlefight.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Race (
    @PrimaryKey()
    val name: String){
    @ColumnInfo(name = "wins")
    var wins: Int = 0
    @ColumnInfo(name = "loses")
    var loses: Int = 0

}