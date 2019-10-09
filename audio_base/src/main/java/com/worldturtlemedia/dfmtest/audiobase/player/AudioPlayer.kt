package com.worldturtlemedia.dfmtest.audiobase.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.github.ajalt.timberkt.e
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

typealias OnStateChange = (state: PlayerState) -> Unit

class AudioPlayer {

    private var state: PlayerState =
        PlayerState.Idle
        set(value) {
            onStateChanged.invoke(value)
            field = value
        }

    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnCompletionListener {
                state = PlayerState.Finished
                reset()
            }
        }
    }

    private var onStateChanged: OnStateChange = {}

    fun setOnStateChangedListener(block: OnStateChange) {
        onStateChanged = block
        block(state)
    }

    suspend fun playFile(filename: String): Boolean {
        return prepareMediaPlayer(filename) && start()
    }

    fun start(): Boolean = try {
        mediaPlayer.start()
        state = PlayerState.Playing
        true
    } catch (error: Throwable) {
        e(error) { "Unable to Start the MediaPlayer!" }
        false
    }

    fun stop(): Boolean = try {
        mediaPlayer.stop()
        mediaPlayer.reset()
        state = PlayerState.Idle
        true
    } catch (error: Throwable) {
        e(error) { "Unable to Stop the MediaPlayer!" }
        false
    }

    fun destroy() {
        try {
            mediaPlayer.stop()
            mediaPlayer.release()
            state = PlayerState.Released
        } catch (error: Throwable) {
            e(error) { "Unable to release the MediaPlayer" }
        }
    }

    private suspend fun prepareMediaPlayer(filename: String): Boolean {
        // TODO: Check if file exists

        return suspendCancellableCoroutine { continuation ->
            try {
                with (mediaPlayer) {
                    setDataSource(filename)
                    setOnPreparedListener {
                        state = PlayerState.Idle
                        continuation.resume(true)
                    }
                    prepareAsync()
                }
            } catch (error: Throwable) {
                e(error) { "Unable to prepare media player with $filename" }
                state =
                    PlayerState.Error(PlayerError.Unknown)
                continuation.resume(false)
            }
        }
    }
}