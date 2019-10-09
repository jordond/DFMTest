package com.worldturtlemedia.dfmtest.ui.audio

import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import java.io.File

class AudioPlayerFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_player_fragment

    private val viewModel: AudioPlayerViewModel by viewModels()

    override fun subscribeViewModel() {
        viewModel.state.observe(owner) { state ->

        }
    }
}
