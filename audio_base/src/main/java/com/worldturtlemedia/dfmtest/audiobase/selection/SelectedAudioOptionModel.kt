package com.worldturtlemedia.dfmtest.audiobase.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.common.ktx.mutableLiveDataOf

class SelectedAudioOptionModel : ViewModel() {

    private val _selectedOption = mutableLiveDataOf<AudioOption>()
    val selectedOption: LiveData<AudioOption>
        get() = _selectedOption

    fun setSelected(audioOption: AudioOption?) {
        _selectedOption.value = audioOption
    }
}