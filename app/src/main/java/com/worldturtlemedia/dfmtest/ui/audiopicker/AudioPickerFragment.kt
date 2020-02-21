package com.worldturtlemedia.dfmtest.ui.audiopicker

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.splitinstall.SplitInstallException
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.audiobase.selection.SelectedAudioOptionModel
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.base.navigate
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.features.Feature
import com.worldturtlemedia.dfmtest.common.features.FeatureManager
import com.worldturtlemedia.dfmtest.common.features.runWithFeature
import kotlinx.android.synthetic.main.audio_picker_fragment.*
import kotlinx.coroutines.launch

class AudioPickerFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_picker_fragment

    private val viewModel: AudioPickerModel by viewModels()
    private val selectedAudioModel: SelectedAudioOptionModel by activityViewModels()

    override fun setupViews() {
        // Ideally we would setup the bottom bar with the Navigation component
        // But we just need a back button, so we can easily implement it.
        bar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        fab.isEnabled = true
        fab.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch { launchAudioPickerScreen() }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun observeViewModel() {
        selectedAudioModel.selectedOption.observe(owner) { selected ->
            txtSelected.text = "Selected:\n${getString(selected.label)} - res: ${selected.rawRes}"
        }
    }

    private suspend fun launchAudioPickerScreen() {
        fab.isEnabled = false
        FeatureManager.instance.runWithFeature(Feature.AudioFull, onFailure = ::installFailed) {
            navigate(AudioPickerFragmentDirections.toAudioPicker())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun installFailed(error: Throwable) {
        fab.isEnabled = true
        txtSelected.text = "Unable to load audio list!\n${error.message}"
    }
}