package com.worldturtlemedia.dfmtest.audiobase.player

sealed class PlayerState {
    object None : PlayerState()
    object Idle : PlayerState()
    object Playing : PlayerState()
    object Finished : PlayerState()
    object Released : PlayerState()
    data class Error(val error: PlayerError) : PlayerState()
}

sealed class PlayerError {
    object FileNotFound : PlayerError()
    data class StartFailed(val error: Throwable) : PlayerError()
    data class StopFailed(val error: Throwable) : PlayerError()
    data class DestroyFailed(val error: Throwable) : PlayerError()
    data class Unknown(val error: Throwable) : PlayerError()
}

val PlayerState.canPlay: Boolean
    get() = this is PlayerState.Idle || this is PlayerState.Finished