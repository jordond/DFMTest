package com.worldturtlemedia.dfmtest.audiobase.assets

import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.audiobase.models.AudioFileOption
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.IllegalStateException

class AudioAssetManager(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private var audioFiles = mutableListOf<AudioFileOption>()

    suspend fun getAudioFiles(activity: Activity): List<AudioFileOption> {
        if (audioFiles.isNotEmpty()) {
            i { "Returning cached list of assets." }
            return audioFiles
        }

        if (!activity.assetPath.exists() || !hasAudioFiles(activity)) {
            val stream = getZipStream(activity)
            unzip(stream, activity.assetPath, dispatcher = dispatcher)
        }

        if (!hasAudioFiles(activity)) {
            throw IllegalStateException("Files don't exist after extracting!")
        }

        audioFiles = createAudioFileList(activity).mapToAudioFileOption().toMutableList()
        return audioFiles
    }

    private fun createAudioFileList(activity: Activity): List<File> {
        return activity.assetPath.list()?.toList()?.map { File(it) } ?: emptyList()
    }

    private fun hasAudioFiles(activity: Activity) = createAudioFileList(activity).isNotEmpty()

    private fun getZipStream(activity: Activity): InputStream {
        return try {
            activity.assetManager.open(RAW_ASSETS_FILENAME)
                ?: throw IOException("Steam was null")
        } catch (error: IOException) {
            e(error) { "Unable to open $RAW_ASSETS_FILENAME, is the module installed?" }
            throw IOException("Unable to open assets, is the module installed?")
        }
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

private val Activity.assetManager: AssetManager
    get() = createPackageContext(packageName, 0).assets