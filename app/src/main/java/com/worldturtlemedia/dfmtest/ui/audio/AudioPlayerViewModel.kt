package com.worldturtlemedia.dfmtest.ui.audio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AudioPlayerViewModel : ViewModel() {

    private val _state: MutableLiveData<AudioPlayerState> = MutableLiveData(AudioPlayerState())
    val state: LiveData<AudioPlayerState>
        get() = _state

}

data class AudioPlayerState(
    val selectedAudio: AudioOption? = null,
    val isPlaying: Boolean = false
)