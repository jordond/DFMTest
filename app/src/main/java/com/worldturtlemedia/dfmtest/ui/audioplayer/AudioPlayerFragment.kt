package com.worldturtlemedia.dfmtest.ui.audioplayer

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.ktx.observe
import kotlinx.android.synthetic.main.audio_picker_fragment.*

class AudioPlayerFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_player_fragment

    private val viewModel: AudioPlayerViewModel by viewModels()

    override fun setupViews() {
        // Ideally we would setup the bottom bar with the Navigation component
        // But we just need a back button, so we can easily implement it.
        bar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        fab.setOnClickListener {

        }
    }

    override fun subscribeViewModel() {
        viewModel.state.observe(owner) { state ->

        }
    }
}
