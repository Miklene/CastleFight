package com.miklene.castlefight.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.miklene.castlefight.model.Ban
import com.miklene.castlefight.model.Round

class RoundConverter {

    @TypeConverter
    fun roundsListToJson(rounds: List<Round>): String {
        return Gson().toJson(rounds)
    }

    @TypeConverter
    fun JsonToRoundList(gson: String): List<Round> {
        val objects = Gson().fromJson(gson, Array<Round>::class.java) as List<Round>
        return objects.toList()
    }
}