package com.worldturtlemedia.dfmtest.ui.audioplayer

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.bindingadapters.visibleOrGone
import com.worldturtlemedia.dfmtest.common.ktx.cast
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.viewmodel.observeProperty
import com.worldturtlemedia.dfmtest.features.Feature
import com.worldturtlemedia.dfmtest.features.FeatureManagerModel
import com.worldturtlemedia.dfmtest.features.runWithFeature
import com.worldturtlemedia.dfmtest.ui.main.MainActivity
import kotlinx.android.synthetic.main.audio_player_fragment.*
import kotlinx.coroutines.launch

class AudioPlayerFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_player_fragment

    private val viewModel: AudioPlayerModel by viewModels()

    private val featureManagerModel: FeatureManagerModel by activityViewModels()

    override fun setupViews() {
        // Ideally we would setup the bottom bar with the Navigation component
        // But we just need a back button, so we can easily implement it.
        bar.setNavigationOnClickListener { findNavController().popBackStack() }

        btnPrevious.setOnClickListener { viewModel.previous() }

        btnNext.setOnClickListener { viewModel.next() }

        fab.setOnClickListener { viewModel.togglePlayer() }

        btnLoad.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                featureManagerModel.runWithFeature(Feature.AudioRaw) {
                    viewModel.getAudioFiles(requireActivity())
                }
            }
        }
    }

    override fun subscribeViewModel() {
        viewModel.state.observe(owner) { state ->
            i { "Received state: $state" }

            requireActivity().cast<MainActivity>()?.setLoading(state.isLoadingAssets)

            with(fab) {
                setImageResource(if (state.isPlaying) R.drawable.ic_stop else R.drawable.ic_play)
                visibleOrGone = state.audioFiles.isNotEmpty()
            }

            with(txtNowPlaying) {
                visibleOrGone = state.selected != null
                text = state.selected
                    ?.let { option ->
                        "Selected #${state.selectedAudio} - ${getString(option.label)}"
                    }
                    ?: "Nothing Selected"
            }
        }

        viewModel.state.observeProperty(owner, { it.audioFiles }) { audioFiles ->
            btnLoad.visibleOrGone = audioFiles.isEmpty()
            btnPrevious.visibleOrGone = audioFiles.isNotEmpty()
            btnNext.visibleOrGone = audioFiles.isNotEmpty()

            if (audioFiles.isEmpty()) return@observeProperty

            otherText.text =
                "Found ${audioFiles.size} files:\n${audioFiles.joinToString { getString(it.label) }}"
        }
    }
}