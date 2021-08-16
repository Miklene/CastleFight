package com.miklene.castlefight.mvvm

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.repositories.FightRepository
import com.miklene.castlefight.repositories.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class PlayerViewModel(@NonNull application: Application) :
    AndroidViewModel(application) {

    private val playerRepository: PlayerRepository
    private lateinit var callback: PlayerCallback
    private lateinit var playLiveData: LiveData<List<Player>>

    fun attachPlayerCallBack(_callback:PlayerCallback){
        callback = _callback
    }
    interface PlayerCallback {
        suspend fun updatePlayerUi(player: Player)
    }

    init {
        playerRepository = PlayerRepository(application)
        viewModelScope.launch {
            playLiveData = playerRepository.getAllPlayers()
        }
    }

    fun getAllPlayers(): LiveData<List<Player>> {
        return playLiveData
    }

   fun getByName(name: String) {
       viewModelScope.launch(Dispatchers.IO){
            val player = playerRepository.getPlayerByName(name)
            try {
                callback.updatePlayerUi(player)
            } catch (e: NullPointerException){
                Log.d("Callback", "PlayerViewModel not attached callback")
            }
        }
    }

}