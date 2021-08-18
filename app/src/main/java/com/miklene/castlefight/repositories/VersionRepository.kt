package com.miklene.castlefight.repositories

import android.content.SharedPreferences


class VersionRepository(val sharedPreferences: SharedPreferences) {
    private val PREFS_VERSION = "VERSION"

    fun loadVersion():Int {
        return sharedPreferences.getInt(PREFS_VERSION, 0)

    }

    fun saveVersion(version:Int) {
        sharedPreferences.edit().putInt(PREFS_VERSION, version).apply()
    }
}