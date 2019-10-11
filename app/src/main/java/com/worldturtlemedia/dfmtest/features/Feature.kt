package com.worldturtlemedia.dfmtest.features

import java.lang.IllegalArgumentException

typealias Features = List<Feature>

sealed class Feature(val name: String) {
    object AudioRaw : Feature(":audio") // TODO change to :audioraw
    object AudioFull : Feature(":audioFull")

    companion object {
        val all = listOf(AudioRaw, AudioFull)

        fun of(string: String) = all.find { it.name == string }
            ?: throw IllegalArgumentException("Could not match $string to an existing module!")
    }
}

val Features.display: String
    get() = joinToString(", ") { it.name.replace(":", "") }