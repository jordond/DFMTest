package com.worldturtlemedia.dfmtest.audio

import com.worldturtlemedia.dfmtest.audiobase.models.AudioOption
import com.worldturtlemedia.dfmtest.audio.R

object AudioFiles {

    val NoAudio = AudioOption(R.string.audio_none, null)

    val options = listOf(
        NoAudio,
        AudioOption(
            R.string.audio_be_chillin,
            R.raw.be_chillin
        ),
        AudioOption(
            R.string.audio_behind_enemy_lines,
            R.raw.behind_enemy_lines
        ),
        AudioOption(
            R.string.audio_big_eyes,
            R.raw.big_eyes
        ),
        AudioOption(
            R.string.audio_city_sunshine,
            R.raw.city_sunshine
        ),
        AudioOption(
            R.string.audio_cornfield_chase,
            R.raw.cornfield_chase
        ),
        AudioOption(
            R.string.audio_epic_boss_battle,
            R.raw.epic_boss_battle
        ),
        AudioOption(
            R.string.audio_funshine,
            R.raw.funshine
        ),
        AudioOption(
            R.string.audio_happy_whistling_ukulele,
            R.raw.happy_whistling_ukulele
        ),
        AudioOption(
            R.string.audio_inspiration,
            R.raw.inspiration
        ),
        AudioOption(
            R.string.audio_motions,
            R.raw.motions
        ),
        AudioOption(
            R.string.audio_pickled_pink,
            R.raw.pickled_pink
        ),
        AudioOption(
            R.string.audio_stereotype_news,
            R.raw.stereotype_news
        ),
        AudioOption(
            R.string.audio_ukulele_song,
            R.raw.ukulele_song
        )
    )
}