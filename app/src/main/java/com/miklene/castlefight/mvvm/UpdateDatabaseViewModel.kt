package com.miklene.castlefight.mvvm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.repositories.VersionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*

class UpdateDatabaseViewModel(@NonNull application: Application) : AndroidViewModel(application) {
    private val databaseFile = "fights.txt"
    var file = File(application.getExternalFilesDir(null), databaseFile)
    private lateinit var callback: DatabaseUpdateCallback
    private val sharedpreferences: SharedPreferences
    private val versionRepository: VersionRepository
    private var version: String = ""

    init {
        sharedpreferences =
            application.getSharedPreferences("preference_key", Context.MODE_PRIVATE)
        versionRepository = VersionRepository(sharedpreferences)
    }

    interface DatabaseUpdateCallback {
        fun databaseLoadingComplete(fights: MutableList<Fight>)
        fun databaseIsGood()
        fun cantFindDatabase()
    }

    fun attachCallback(databaseUpdateCallback: DatabaseUpdateCallback) {
        callback = databaseUpdateCallback

    }

    fun loadUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!file.exists()) {
                callback.cantFindDatabase()
            } else {
                try {
                    val inputStream = FileInputStream(file)
                    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String?
                    try {
                        while ((bufferedReader.readLine().also { line = it }) != null) {
                            stringBuilder.append(line);
                        }
                        bufferedReader.close()
                        getFightsFromStringDatabase(stringBuilder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        bufferedReader.close()
                        callback.cantFindDatabase()
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    callback.cantFindDatabase()
                }
            }
        }
    }

    private fun getFightsFromStringDatabase(database: java.lang.StringBuilder) {
        var fights: MutableList<Fight> = mutableListOf()
        var position: Int = 0
        var myVersion = versionRepository.loadVersion()
        var winnerName: String
        var loserName: String
        var winnerRace: String
        var loserRace: String
        var end = database.indexOf(";")
        version = database.substring(end - 1, end)
        database.delete(position, end + 1)
        if (myVersion != version.toInt()) {
            end = database.indexOf((++myVersion).toString())
            database.delete(position, end + 2)
            while (position < database.length) {
                if(database.indexOf(";") < 2) {
                    end = database.indexOf(";")
                    database.delete(position, end + 1)
                }
                end = database.indexOf(",")
                winnerName = database.substring(position, end)
                database.delete(position, end + 1)
                end = database.indexOf(",")
                loserName = database.substring(position, end)
                database.delete(position, end + 1)
                end = database.indexOf(",")
                winnerRace = database.substring(position, end)
                database.delete(position, end + 1)
                end = database.indexOf(";")
                loserRace = database.substring(position, end)
                database.delete(position, end + 1)
                fights.add(Fight(0, winnerName, loserName, winnerRace, loserRace))
            }
            callback.databaseLoadingComplete(fights)
            return
        }
        callback.databaseIsGood()
        return
    }

    fun updateDatabaseVersion() {
        if (version != "")
            versionRepository.saveVersion(version.toInt())
    }
}