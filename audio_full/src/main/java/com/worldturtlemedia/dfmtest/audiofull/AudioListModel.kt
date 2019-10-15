package com.worldturtlemedia.dfmtest.audiofull

import android.content.Context
import androidx.lifecycle.LiveData
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.audiobase.AudioPlayerUseCase
import com.worldturtlemedia.dfmtest.audiobase.AudioPlayerViewModel
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState
import com.worldturtlemedia.dfmtest.common.ktx.mediatorLiveDataOf

class AudioListModel : AudioPlayerViewModel(
    // Ideally be injected
    AudioPlayerUseCase()
) {

    private val _state = mediatorLiveDataOf(AudioListState())
    val state: LiveData<AudioListState>
        get() = _state

    private val currentState: AudioListState
        get() = _state.value!!

    init {
        _state.addSource(audioPlayer.playerState) { status ->
            i { "Player state: $status" }
            updateState { copy(status = status) }
        }
    }

    fun audioItemClicked(context: Context, audioOption: AudioOption) {
        i { "Current Player state: ${currentState.status}" }
        when {
            currentState.status is PlayerState.Playing ->
                if (currentState.selected != audioOption) play(context, audioOption)
                else stop()
            currentState.status is PlayerState.Idle -> play(context, audioOption)
        }
    }

    private fun play(context: Context, audioOption: AudioOption) {
        updateState { copy(selected = audioOption) }
        audioPlayer.play(context, audioOption)
    }

    private fun stop() {
        audioPlayer.stop()
        updateState { copy(selected = null) }
    }

    private fun updateState(block: AudioListState.() -> AudioListState) {
        _state.value = block(currentState)
    }

    override fun onCleared() {
        i { "Clearing VM, time to destroy!" }
        audioPlayer.destroy()
        super.onCleared()
    }
}

data class AudioListState(
    val selected: AudioOption? = null,
    val status: PlayerState = PlayerState.None
)