package com.worldturtlemedia.dfmtest.audiobase.assets

import android.app.Activity
import android.content.Context
import com.github.ajalt.timberkt.d
import com.worldturtlemedia.dfmtest.audiobase.models.AudioFileOption
import java.io.File

class AudioAssetManager {

    private var _audioFiles = mutableListOf<AudioFileOption>()
    val audioFiles: List<AudioFileOption>
        get() = _audioFiles

    // Step 1: Check if audio files exist
    //      Check if zip file exists
    //          If not, throw error, because module isn't installed
    //          If so, unzip the files to `assetPath`
    //
    // Step 2: Get a list of all files in `assetPath`

    suspend fun init(activity: Activity) {
        if (_audioFiles.isNotEmpty()) return

        assetPath(activity).list().toList().isNotEmpty()
        val assetManager = activity.assetManager


    }

    private fun hasExtractedAssets(activity: Activity) {
        activity.assetPath.list().toList()
            .also { list -> d { "List of assets: $list" } }
            .isNotEmpty()
    }

    private fun createAudioFileList(activity: Activity) {
        activity.assetPath.list().toList().map { File(it) }
    }

    companion object {

        const val RAW_ASSETS_FILENAME = "audio_files.zip"

        const val AUDIO_ASSETS_FOLDER = "audio_assets"

        // Ideally be injected
        val instance by lazy { AudioAssetManager() }

        fun assetPath(context: Context) = File(context.filesDir, AUDIO_ASSETS_FOLDER)
    }
}

val Context.assetPath: File
    get() = AudioAssetManager.assetPath(this)

private val Activity.assetManager: Context
    get() = createPackageContext(packageName, 0)