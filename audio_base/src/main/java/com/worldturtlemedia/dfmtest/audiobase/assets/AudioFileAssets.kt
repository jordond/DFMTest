package com.worldturtlemedia.dfmtest.audiobase.assets

import com.worldturtlemedia.dfmtest.audiobase.R
import com.worldturtlemedia.dfmtest.audiobase.models.AudioFileOption
import java.io.File

object AudioFileAssets {

    val NoAudio = AudioFileOption(R.string.audio_none, null)

    fun mapFileToAudioAsset(file: File): AudioFileOption {
        val stringRes = AUDIO_ASSET_MAP[file.name]
            ?: throw IllegalArgumentException("Unable to map ${file.name} to a known asset.")

        return AudioFileOption(stringRes, file)
    }

    private val AUDIO_ASSET_MAP = mapOf(
        "be_chillen.mp3" to R.string.audio_be_chillin,
        "behind_enemy_lines.mp3" to R.string.audio_behind_enemy_lines,
        "big_eyes.mp3" to R.string.audio_big_eyes,
        "city_sunshine.mp3" to R.string.audio_city_sunshine,
        "cornfield_chase.mp3" to R.string.audio_cornfield_chase,
        "epic_boss_battle.mp3" to R.string.audio_epic_boss_battle,
        "funshine.mp3" to R.string.audio_funshine,
        "happy_whistling_ukulele.mp3" to R.string.audio_happy_whistling_ukulele,
        "inspiration.mp3" to R.string.audio_inspiration,
        "motions.mp3" to R.string.audio_motions,
        "picked_pink.mp3" to R.string.audio_pickled_pink,
        "stereotype_news.mp3" to R.string.audio_stereotype_news,
        "ukulele_song.mp3" to R.string.audio_ukulele_song
    )
}