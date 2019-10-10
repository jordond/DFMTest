package com.worldturtlemedia.dfmtest.ui.audioplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption

class AudioPlayerViewModel : ViewModel() {

    private val _state: MutableLiveData<AudioPlayerState> = MutableLiveData(AudioPlayerState())
    val state: LiveData<AudioPlayerState>
        get() = _state

}

data class AudioPlayerState(
    val selectedAudio: AudioOption? = null,
    val isPlaying: Boolean = false
)