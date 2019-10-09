package com.worldturtlemedia.dfmtest.audiobase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.worldturtlemedia.dfmtest.audiobase.player.AudioPlayer
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState

class AudioPlayerModel : ViewModel() {

    private val audioPlayer: AudioPlayer =
        AudioPlayer()

    private val _playerState: MutableLiveData<PlayerState> = MutableLiveData(
        PlayerState.None)
    val playerState: LiveData<PlayerState>
        get() = _playerState

    private val currentState: PlayerState
        get() = _playerState.value ?: PlayerState.None

    init {
        audioPlayer.setOnStateChangedListener { state ->
            _playerState.postValue(state)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.destroy()
    }
}