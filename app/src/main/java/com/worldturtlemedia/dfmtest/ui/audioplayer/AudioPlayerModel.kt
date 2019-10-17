package com.worldturtlemedia.dfmtest.ui.audioplayer

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldturtlemedia.dfmtest.audiobase.assets.AudioAssetManager
import com.worldturtlemedia.dfmtest.audiobase.models.AudioFileOption
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.player.AudioPlayerUseCase
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState
import com.worldturtlemedia.dfmtest.common.viewmodel.State
import com.worldturtlemedia.dfmtest.common.viewmodel.StateViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AudioPlayerModel : StateViewModel<AudioPlayerState>(AudioPlayerState()) {

    private val audioAssetManager = AudioAssetManager.instance

    // TODO: Currently the AudioPlayer is only setup to handle AudioOptions (with RAW assets)
    private val audioPlayerUseCase = AudioPlayerUseCase()

    init {
        addStateSource(audioPlayerUseCase.playerState) { state, status ->
            // TODO: This is how the play state would be updated, if the player was working
//            state.copy(isPlaying = status is PlayerState.Playing)
            state
        }
    }

    /**
     * The feature module: "audio_raw" needs to be available!
     * Will throw if the module has not yet been installed!
     */
    fun getAudioFiles(activity: Activity) = viewModelScope.launch {
        // TODO: Move feature installer to a singleton
        // Ensure feature is installed before the following

        updateState { copy(isLoadingAssets = true) }

        // TODO: Wrap this in error handling and update UI on failure
        val list = audioAssetManager.getAudioFiles(activity)
        updateState {
            copy(
                isLoadingAssets = false,
                audioFiles = list,
                selectedAudio = 0
            )
        }

        // Uninstall the asset module
    }

    fun togglePlayer() {
        // TODO: Play or stop the music
        // Right now we just manually update `isPlaying` but ideally it should be handled above

        updateState { copy(isPlaying = !isPlaying) }
    }

    fun next() {
        val newIndex = currentState.selectedAudio + 1
        if (newIndex > currentState.audioFiles.size - 1) {
            // TODO: stop the music
            updateState { copy(isPlaying = false) }
        } else {
            updateState { copy(selectedAudio = selectedAudio + 1) }
            // TODO: Play the file
        }
    }

    fun previous() {
        val newIndex = currentState.selectedAudio - 1
        if (newIndex < 0) {
            // TODO: Stop the player
            updateState { copy(isPlaying = false) }
        } else {
            updateState { copy(selectedAudio = selectedAudio - 1) }
        }
    }

    override fun onCleared() {
        audioPlayerUseCase.destroy()
        super.onCleared()
    }
}

data class AudioPlayerState(
    val selectedAudio: Int = -1,
    val isPlaying: Boolean = false,
    val isLoadingAssets: Boolean = false,
    val audioFiles: List<AudioFileOption> = emptyList()
) : State

val AudioPlayerState.selected: AudioFileOption?
    get() = audioFiles.getOrNull(selectedAudio)
