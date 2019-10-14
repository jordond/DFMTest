package com.worldturtlemedia.dfmtest.audiobase

import android.content.Context
import androidx.lifecycle.LiveData
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.player.AudioPlayer
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState
import com.worldturtlemedia.dfmtest.common.ktx.mutableLiveDataOf

class AudioPlayerUseCase {

    private val audioPlayer: AudioPlayer = AudioPlayer()

    private val _playerState = mutableLiveDataOf<PlayerState>(PlayerState.None)
    val playerState: LiveData<PlayerState>
        get() = _playerState

    init {
        audioPlayer.setOnStateChangedListener { state ->
            _playerState.postValue(state)
        }
    }

    fun play(context: Context, option: AudioOption): Boolean {
        if (option.rawRes == null) {
            i { "Can't play ${option.label} as it has no Resource ID" }
            return false
        }

        return try {
            audioPlayer.playFile(context, option.rawRes)
        } catch (error: Throwable) {
            e(error) { "Unable to play ${context.getString(option.label)}" }
            false
        }
    }

    fun stop() = audioPlayer.stop()

    fun destroy() {
        audioPlayer.destroy()
    }
}
