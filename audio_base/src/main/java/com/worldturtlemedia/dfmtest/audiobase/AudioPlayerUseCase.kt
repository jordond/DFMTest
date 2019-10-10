package com.worldturtlemedia.dfmtest.audiobase

import android.content.Context
import androidx.lifecycle.LiveData
import com.github.ajalt.timberkt.e
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.models.filePath
import com.worldturtlemedia.dfmtest.audiobase.player.AudioPlayer
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState
import com.worldturtlemedia.dfmtest.common.ktx.mutableLiveDataOf

// TODO
/**
 * Need to utilize the use-case in the `audio` module
 * It needs to play the music, then update the UI with which one is playing
 *
 * `app` module, then needs to launch the fragment by using a bottomsheet? using the
 * fragment from the audio module.  DFM!
 *
 * Maybe do navigation?
 */

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

    suspend fun play(context: Context, option: AudioOption): Boolean {
        val uri = option.filePath(context) ?: return false

        return try {
            audioPlayer.playFile(context, uri)
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
