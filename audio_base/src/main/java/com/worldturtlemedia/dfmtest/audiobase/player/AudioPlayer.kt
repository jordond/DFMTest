package com.worldturtlemedia.dfmtest.audiobase.player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes
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

    suspend fun playFile(context: Context, fileUri: Uri): Boolean {
        return prepareMediaPlayer(context, fileUri) && start()
    }

    private fun start(): Boolean = try {
        mediaPlayer.start()
        state = PlayerState.Playing
        true
    } catch (error: Throwable) {
        e(error) { "Unable to Start the MediaPlayer!" }
        state = PlayerState.Error(PlayerError.StartFailed(error))
        false
    }

    fun stop(): Boolean = try {
        mediaPlayer.stop()
        mediaPlayer.reset()
        state = PlayerState.Idle
        true
    } catch (error: Throwable) {
        e(error) { "Unable to Stop the MediaPlayer!" }
        state = PlayerState.Error(PlayerError.StopFailed(error))
        false
    }

    fun destroy() {
        state = try {
            mediaPlayer.stop()
            mediaPlayer.release()
            PlayerState.Released
        } catch (error: Throwable) {
            e(error) { "Unable to release the MediaPlayer" }
            PlayerState.Error(PlayerError.DestroyFailed(error))
        }
    }

    private suspend fun prepareMediaPlayer(context: Context, fileUri: Uri): Boolean {
        return suspendCancellableCoroutine { continuation ->
            try {
                with(mediaPlayer) {
                    setDataSource(context.applicationContext, fileUri)
                    setOnPreparedListener {
                        state = PlayerState.Idle
                        continuation.resume(true)
                    }
                    prepareAsync()
                }
            } catch (error: Throwable) {
                e(error) { "Unable to prepare media player with $fileUri" }
                state = PlayerState.Error(PlayerError.Unknown(error))
                continuation.resume(false)
            }
        }
    }
}