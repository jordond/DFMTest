package com.worldturtlemedia.dfmtest.audiofull

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.e
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audiobase.player.PlayerState
import com.worldturtlemedia.dfmtest.audiobase.selection.SelectedAudioOptionModel
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.ktx.cast
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.ui.groupieAdapter
import com.worldturtlemedia.dfmtest.common.base.viewmodel.sharedViewModels
import com.worldturtlemedia.dfmtest.common.base.viewmodel.viewModels
import kotlinx.android.synthetic.main.audio_list_fragment.*
import kotlinx.coroutines.launch

class AudioListFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_list_fragment

    private val viewModel: AudioListModel by viewModels()
    private val selectedOptionModel: SelectedAudioOptionModel by sharedViewModels()

    private val listAdapter =
        groupieAdapter {
            setOnItemClickListener { item, _ ->
                val option = item.cast<AudioListItem>() ?: return@setOnItemClickListener

                selectedOptionModel.setSelected(option.audioOption)
                navigateBack()
            }
        }

    override fun setupViews() {
        with(rvAudio) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        createInitialListItems()
    }

    override fun subscribeViewModel() {
        viewModel.state.observe(owner) { state ->
            when(state.status) {
                is PlayerState.Playing -> refreshList(state.selected)
                else -> refreshList(null)
            }

            selectedOptionModel.setSelected(state.selected)
        }
    }

    private fun createInitialListItems() {
        // Build the list items
        val items = AudioFiles.options.map { option ->
            AudioListItem(option, false, ::onAudioItemClicked)
        }

        listAdapter.updateAsync(items)
    }

    private fun refreshList(option: AudioOption?) {
        val items = AudioFiles.options.map { current ->
            AudioListItem(current, isPlaying = option == current, onMediaToggle = ::onAudioItemClicked)
        }

        listAdapter.updateAsync(items)
    }

    private fun onAudioItemClicked(option: AudioOption) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.audioItemClicked(requireContext(), option)
        }
    }

    private fun navigateBack() {
        // Ideally we would have an interface, with an injected implementation, but for easiness
        // we can just pop the backstack (if it exists...)
        try {
            findNavController().popBackStack()
        } catch (error: Throwable) {
            e { "Could not find the nav controller!" }
        }
    }
}