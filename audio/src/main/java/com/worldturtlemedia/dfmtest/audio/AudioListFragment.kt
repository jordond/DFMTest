package com.worldturtlemedia.dfmtest.audio

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.ktx.bind
import com.worldturtlemedia.dfmtest.common.util.groupieAdapter
import com.worldturtlemedia.dfmtest.common.view.LoadingView
import kotlinx.coroutines.delay

class AudioListFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_list_fragment

    private val audioList: RecyclerView by bind(R.id.rvAudio)
    private val loadingView: LoadingView by bind(R.id.loadingView)

    private val listAdapter = groupieAdapter()

    override fun setupViews() {
        with(audioList) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private suspend fun createListItems() {
        loadingView.isLoading = true

        // Artificial delay, for funsies
        delay(3000)

        // Build the list items
        AudioFiles.options
            .map { option ->
                AudioListItem.State(
                    option,
                    isPlaying = false
                )
            }
            .map { state ->
                AudioListItem(
                    state,
                    ::onAudioItemClicked
                )
            }
            .let { items ->
                listAdapter.updateAsync(items) {
                    loadingView.isLoading = false
                }
            }
    }

    private fun onAudioItemClicked(state: AudioListItem.State) {}
}