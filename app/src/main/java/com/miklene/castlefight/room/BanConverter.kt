package com.miklene.castlefight.room

import android.R.attr.data
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miklene.castlefight.model.Ban
import java.lang.reflect.Type
import java.util.*


class BanConverter {

    @TypeConverter
    fun banListToJson(bans: List<Ban>): String {
        return Gson().toJson(bans)
    }

    @TypeConverter
    fun JsonToBanList(gson: String): List<Ban> {
        val objects = Gson().fromJson(gson, Array<Ban>::class.java) as List<Ban>
        return objects.toList()
    }
}