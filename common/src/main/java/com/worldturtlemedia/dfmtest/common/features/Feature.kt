package com.worldturtlemedia.dfmtest.common.features

import com.worldturtlemedia.dfmtest.common.BuildConfig
import java.lang.IllegalArgumentException

typealias Features = List<Feature>

sealed class Feature(val name: String) {
    object AudioRaw : Feature(BuildConfig.FEATURE_MODULE_AUDIO_RAW.replace(":", ""))
    object AudioFull : Feature(BuildConfig.FEATURE_MODULE_AUDIO_FULL.replace(":", ""))

    companion object {
        val all = listOf(AudioRaw, AudioFull)

        fun of(string: String) = all.find { it.name == string }
            ?: throw IllegalArgumentException("Could not match $string to an existing module!")
    }

    override fun toString(): String = name
}

val Features.display: String
    get() = joinToString(", ") { it.name.replace(":", "") }