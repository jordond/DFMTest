package com.worldturtlemedia.dfmtest.audio

import com.worldturtlemedia.dfmtest.R as RApp
import com.worldturtlemedia.dfmtest.common.R as RCommon
import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.common.ktx.cast
import com.worldturtlemedia.dfmtest.common.ktx.color
import com.worldturtlemedia.dfmtest.common.ktx.odd
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.audio_list_item.*

typealias OnAudioItemClicked = (AudioOption) -> Unit

data class AudioListItem(
    val audioOption: AudioOption,
    private val isPlaying: Boolean,
    private val onClicked: OnAudioItemClicked
) : Item() {

    override fun getLayout(): Int = R.layout.audio_list_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val context = viewHolder.root.context

        with(viewHolder) {
            with(root) {
                if (odd(position)) setBackgroundColor(context.color(RCommon.color.grey))
                root.setOnClickListener { onClicked(audioOption) }
            }

            txtLabel.text = context.getString(audioOption.label)
            imgAction.setImageResource(
                if (isPlaying) RApp.drawable.ic_stop else RApp.drawable.ic_play
            )
        }
    }

    override fun getId(): Long = audioOption.hashCode().toLong()

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean =
        other?.cast<AudioListItem>()?.audioOption == audioOption
}