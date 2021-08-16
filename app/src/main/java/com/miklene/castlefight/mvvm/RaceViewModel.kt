package com.miklene.castlefight.mvvm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race
import com.miklene.castlefight.repositories.PlayerRepository
import com.miklene.castlefight.repositories.RaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class RaceViewModel (@NonNull application: Application) : AndroidViewModel(application){

    private val raceRepository: RaceRepository
    private lateinit var callback: RaceCallback
    private lateinit var raceLiveData: LiveData<List<Race>>

    fun attachRaceCallBack(_callback:RaceCallback){
        callback = _callback
    }
    interface RaceCallback {
        suspend fun updateRaceUi(race: Race)
    }

    init {
        raceRepository = RaceRepository(application)
        viewModelScope.launch {
            raceLiveData = raceRepository.getAllRaces()
        }
    }

    fun getAllRaces(): LiveData<List<Race>> {
        return raceLiveData
    }

    fun getByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val race = raceRepository.getRaceByName(name)
            try {
                callback.updateRaceUi(race)
            } catch (e: NullPointerException){
                Log.d("Callback", "RaceViewModel not attached callback")
            }
        }
    }
}