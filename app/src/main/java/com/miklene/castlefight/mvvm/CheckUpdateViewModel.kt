package com.miklene.castlefight.mvvm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.repositories.VersionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*

class CheckUpdateViewModel(@NonNull application: Application) : AndroidViewModel(application) {
    private val databaseFile = "fights.txt"
    private val emptyFileName = "empty.txt"
    private var file = File(application.getExternalFilesDir(null), databaseFile)
    private var emptyFile = File(application.getExternalFilesDir(null), emptyFileName)
    private lateinit var callback: DatabaseCheckUpdateCallback
    private val sharedpreferences: SharedPreferences
    private val versionRepository: VersionRepository
    private var version: String = ""

    init {
        sharedpreferences =
            application.getSharedPreferences("preference_key", Context.MODE_PRIVATE)
        versionRepository = VersionRepository(sharedpreferences)
    }

    interface DatabaseCheckUpdateCallback {
        fun databaseIsOutOfDate(outOfDate: Boolean)
        fun cantFindDatabase()
    }

    fun attachCallback(databaseUpdateCallback: DatabaseCheckUpdateCallback) {
        callback = databaseUpdateCallback

    }

    fun checkUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!file.exists()) {
                if (!emptyFile.exists())
                    emptyFile.createNewFile()
                callback.cantFindDatabase()
            } else {
                try {
                    val inputStream = FileInputStream(file)
                    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    try {
                        stringBuilder.append(bufferedReader.readLine())
                        bufferedReader.close()
                        checkVersion(stringBuilder)
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

    private fun checkVersion(database: java.lang.StringBuilder) {
        val position: Int = 0
        val end = database.indexOf(";")
        version = database.substring(end - 1, end)
        if (versionRepository.loadVersion() < version.toInt())
            callback.databaseIsOutOfDate(true)
        else
            callback.databaseIsOutOfDate(false)
    }
}