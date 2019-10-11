package com.worldturtlemedia.dfmtest.audiofull

import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.common.ktx.cast
import com.worldturtlemedia.dfmtest.common.ktx.color
import com.worldturtlemedia.dfmtest.common.ktx.odd
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.audio_list_item.*
import com.worldturtlemedia.dfmtest.R as RApp
import com.worldturtlemedia.dfmtest.common.R as RCommon

typealias OnAudioItemClicked = (AudioOption) -> Unit

data class AudioListItem(
    val audioOption: AudioOption,
    private val isPlaying: Boolean,
    private val onMediaToggle: OnAudioItemClicked
) : Item() {

    override fun getLayout(): Int = R.layout.audio_list_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val context = viewHolder.root.context

        with(viewHolder) {
            if (odd(position)) root.setBackgroundColor(context.color(RCommon.color.grey))

            txtLabel.text = context.getString(audioOption.label)

            with(imgAction) {
                setOnClickListener { onMediaToggle(audioOption) }
                imgAction.setImageResource(
                    if (isPlaying) RApp.drawable.ic_stop else RApp.drawable.ic_play
                )

            }
        }
    }

    override fun getId(): Long = audioOption.hashCode().toLong()

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean =
        other?.cast<AudioListItem>()?.audioOption == audioOption
}