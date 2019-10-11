package com.worldturtlemedia.dfmtest.audio

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.worldturtlemedia.dfmtest.audiobase.AudioPlayerUseCase
import com.worldturtlemedia.dfmtest.audiobase.AudioPlayerViewModel
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState

class AudioListModel : AudioPlayerViewModel(
    // Ideally be injected
    AudioPlayerUseCase()
) {

    private val _state = MediatorLiveData<AudioListState>().also { it.value = AudioListState() }
    private val currentState: AudioListState
        get() = _state.value!!
    val state: LiveData<AudioListState>
        get() = _state

    init {
        _state.addSource(audioPlayer.playerState) { status ->
            updateState { copy(status = status) }
        }
    }

    fun audioItemClicked(context: Context, audioOption: AudioOption) {

    }

    suspend fun play(context: Context, audioOption: AudioOption) {
        updateState { copy(selected = audioOption) }
        audioPlayer.play(context, audioOption)
    }

    fun stop() {
        audioPlayer.stop()
        updateState { copy(selected = null) }
    }

    private fun updateState(block: AudioListState.() -> AudioListState) {
        _state.value = block(currentState)
    }

    override fun onCleared() {
        audioPlayer.destroy()
        super.onCleared()
    }
}

data class AudioListState(
    val selected: AudioOption? = null,
    val status: PlayerState = PlayerState.None
)