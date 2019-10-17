package com.worldturtlemedia.dfmtest.common.features

import java.lang.IllegalArgumentException

typealias Features = List<Feature>

// TODO: Should read the `name`s from the BuildConfig
sealed class Feature(val name: String) {
    object AudioRaw : Feature("audio_raw")
    object AudioFull : Feature("audio_full")

    companion object {
        val all = listOf(AudioRaw, AudioFull)

        fun of(string: String) = all.find { it.name == string }
            ?: throw IllegalArgumentException("Could not match $string to an existing module!")
    }

    override fun toString(): String = name
}

val Features.display: String
    get() = joinToString(", ") { it.name.replace(":", "") }