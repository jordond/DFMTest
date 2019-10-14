package com.worldturtlemedia.dfmtest.audiobase.player

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import com.github.ajalt.timberkt.e

typealias OnStateChange = (state: PlayerState) -> Unit

/**
 * This was adapted quickly from another project that was geared toward playing a single file.
 *
 * It is a quick and dirty implementation of an media player
 */
class AudioPlayer {

    private var state: PlayerState =
        PlayerState.Idle
        set(value) {
            onStateChanged.invoke(value)
            field = value
        }

    private var mediaPlayer: MediaPlayer? = null

    private var onStateChanged: OnStateChange = {}

    fun setOnStateChangedListener(block: OnStateChange) {
        onStateChanged = block
        block(state)
    }

    fun playFile(context: Context, @RawRes rawRes: Int): Boolean {
        createPlayer(context, rawRes)
        return start()
    }

    private fun start(): Boolean = try {
        mediaPlayer?.start()
        state = PlayerState.Playing
        true
    } catch (error: Throwable) {
        e(error) { "Unable to Start the MediaPlayer!" }
        state = PlayerState.Error(PlayerError.StartFailed(error))
        false
    }

    fun stop(): Boolean = try {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        state = PlayerState.Idle
        true
    } catch (error: Throwable) {
        e(error) { "Unable to Stop the MediaPlayer!" }
        state = PlayerState.Error(PlayerError.StopFailed(error))
        false
    }

    fun destroy() {
        state = try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            PlayerState.Released
        } catch (error: Throwable) {
            e(error) { "Unable to release the MediaPlayer" }
            PlayerState.Error(PlayerError.DestroyFailed(error))
        }
    }

    private fun createPlayer(context: Context, @RawRes rawRes: Int) {
        val player = MediaPlayer.create(context, rawRes).apply {
            setOnCompletionListener {
                state = PlayerState.Finished
                reset()
            }
        }

        mediaPlayer = player
    }
}