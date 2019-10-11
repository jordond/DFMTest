package com.worldturtlemedia.dfmtest.audiobase

import androidx.lifecycle.ViewModel

abstract class AudioPlayerViewModel(
    protected val audioPlayer: AudioPlayerUseCase
) : ViewModel() {

    override fun onCleared() {
        audioPlayer.destroy()
        super.onCleared()
    }
}