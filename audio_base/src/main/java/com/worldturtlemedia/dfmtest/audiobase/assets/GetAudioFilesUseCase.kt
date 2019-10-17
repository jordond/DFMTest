package com.worldturtlemedia.dfmtest.audiobase.assets

import android.app.Activity
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.audiobase.models.AudioFileOption
import com.worldturtlemedia.dfmtest.common.features.Feature
import com.worldturtlemedia.dfmtest.common.features.FeatureManager
import com.worldturtlemedia.dfmtest.common.features.runWithFeature

class GetAudioFilesUseCase(
    private val featureManager: FeatureManager = FeatureManager.instance,
    private val assetManager: AudioAssetManager = AudioAssetManager.instance
) {

    suspend fun execute(activity: Activity): List<AudioFileOption> {
        // Files already exist, no need to install the module
        if (assetManager.hasAudioFiles(activity)) return assetManager.getAudioFiles(activity)

        // Ensure the feature module exists, then get the audioFiles
        val list = featureManager.runWithFeature(Feature.AudioRaw) {
            assetManager.unzipAudioFiles(activity)
            assetManager.getAudioFiles(activity)
        }

        i { "Found ${list.size} files!" }

        // The audio_raw module is no longer needed because the zip file has been extracted
        // We will still need to handle a use-case where the audio files have been deleted
        // and the module is required again so we can unzip them again
        featureManager.uninstall(Feature.AudioRaw)

        return list
    }
}