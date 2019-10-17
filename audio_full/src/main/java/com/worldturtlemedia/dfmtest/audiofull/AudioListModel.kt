package com.worldturtlemedia.dfmtest.audiofull

import android.content.Context
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.audiobase.player.AudioPlayerUseCase
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState
import com.worldturtlemedia.dfmtest.common.viewmodel.State
import com.worldturtlemedia.dfmtest.common.viewmodel.StateViewModel

class AudioListModel : StateViewModel<AudioListState>(AudioListState()) {

    private val audioPlayer: AudioPlayerUseCase = AudioPlayerUseCase()

    init {
        addStateSource(audioPlayer.playerState) { state, status ->
            i { "Player state: $status" }
            state.copy(status = status)
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

    override fun onCleared() {
        audioPlayer.destroy()
        super.onCleared()
    }
}

data class AudioListState(
    val selected: AudioOption? = null,
    val status: PlayerState = PlayerState.None
) : State