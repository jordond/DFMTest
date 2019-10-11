package com.worldturtlemedia.dfmtest.audio

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.util.groupieAdapter
import com.worldturtlemedia.dfmtest.common.viewmodel.viewModels
import kotlinx.android.synthetic.main.audio_list_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioListFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_list_fragment

    private val viewModel: AudioListModel by viewModels()

    private val listAdapter = groupieAdapter()

    override fun setupViews() {
        with(rvAudio) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            createInitialListItems()
        }
    }

    override fun subscribeViewModel() {
        viewModel.state.observe(owner) { state ->
            when(state.status) {
                is PlayerState.Playing -> refreshList(state.selected)
                else -> refreshList(null)
            }
        }
    }

    private suspend fun createInitialListItems() {
        loadingView.setLoading(true, "Loading the audio files")

        // Artificial delay, for funsies
        delay(3000)

        // Build the list items
        val items = AudioFiles.options.map { option ->
            AudioListItem(option, false, ::onAudioItemClicked)
        }
        listAdapter.updateAsync(items) {
            loadingView.setLoading(false)
        }
    }

    private fun refreshList(option: AudioOption?) {
        val items = AudioFiles.options.map { current ->
            AudioListItem(current, isPlaying = option == current, onClicked = ::onAudioItemClicked)
        }

        listAdapter.updateAsync(items)
    }

    private fun onAudioItemClicked(option: AudioOption) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.play(requireContext(), option)
        }
    }
}