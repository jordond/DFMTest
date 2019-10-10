package com.worldturtlemedia.dfmtest.ui.audiopicker

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import kotlinx.android.synthetic.main.audio_picker_fragment.*

class AudioPickerFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_picker_fragment

    private val viewModel: AudioPickerModel by viewModels()

    override fun setupViews() {
        // Ideally we would setup the bottom bar with the Navigation component
        // But we just need a back button, so we can easily implement it.
        bar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        fab.setOnClickListener {

        }
    }
}