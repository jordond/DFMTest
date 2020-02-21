package com.worldturtlemedia.dfmtest.ui.audioplayer

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.worldturtlemedia.dfmtest.audiobase.assets.AudioAssetManager
import com.worldturtlemedia.dfmtest.audiobase.assets.GetAudioFilesUseCase
import com.worldturtlemedia.dfmtest.audiobase.models.AudioFileOption
import com.worldturtlemedia.dfmtest.audiobase.player.AudioPlayerUseCase
import com.worldturtlemedia.dfmtest.common.base.viewmodel.State
import com.worldturtlemedia.dfmtest.common.base.viewmodel.StateViewModel
import kotlinx.coroutines.launch

class AudioPlayerModel : StateViewModel<AudioPlayerState>(AudioPlayerState()) {

    private val audioAssetManager = AudioAssetManager.instance

    private val getAudioFilesUseCase = GetAudioFilesUseCase()

    // TODO: Currently the AudioPlayer is only setup to handle AudioOptions (with RAW assets)
    private val audioPlayerUseCase = AudioPlayerUseCase()

    fun getAudioFiles(activity: Activity) = viewModelScope.launch {
        setState { copy(isLoadingAssets = true) }

        val list = getAudioFilesUseCase.execute(activity)
        setState {
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

        setState { copy(isPlaying = !isPlaying) }
    }

    fun next() {
        val newIndex = currentState.selectedAudio + 1
        if (newIndex > currentState.audioFiles.size - 1) {
            // TODO: stop the music
            setState { copy(isPlaying = false) }
        } else {
            setState { copy(selectedAudio = selectedAudio + 1) }
            // TODO: Play the file
        }
    }

    fun previous() {
        val newIndex = currentState.selectedAudio - 1
        if (newIndex < 0) {
            // TODO: Stop the player
            setState { copy(isPlaying = false) }
        } else {
            setState { copy(selectedAudio = selectedAudio - 1) }
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
