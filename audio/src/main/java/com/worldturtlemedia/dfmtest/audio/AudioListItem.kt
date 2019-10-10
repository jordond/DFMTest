package com.worldturtlemedia.dfmtest.audio

import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.common.ktx.cast
import com.worldturtlemedia.dfmtest.common.ktx.color
import com.worldturtlemedia.dfmtest.common.ktx.odd
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.audio_list_item.*

typealias OnAudioItemClicked = (AudioListItem.State) -> Unit

data class AudioListItem(
    private val state: State,
    private val onClicked: OnAudioItemClicked
) : Item() {

    override fun getLayout(): Int = R.layout.audio_list_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val context = viewHolder.root.context

        with(viewHolder) {
            with(root) {
                if (odd(position)) setBackgroundColor(context.color(R.color.grey))
                root.setOnClickListener { onClicked(state) }
            }

            txtLabel.text = context.getString(state.audioOption.label)
            imgAction.setImageResource(
                if (state.isPlaying) R.drawable.ic_stop else R.drawable.ic_play
            )
        }
    }

    override fun getId(): Long = state.hashCode().toLong()

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean =
        other?.cast<AudioListItem>()?.state == state

    data class State(
        val audioOption: AudioOption,
        val isPlaying: Boolean
    )
}