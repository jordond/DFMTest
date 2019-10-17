package com.worldturtlemedia.dfmtest.audiobase.models

import androidx.annotation.StringRes
import com.worldturtlemedia.dfmtest.audiobase.assets.AudioFileAssets
import java.io.File

data class AudioFileOption(
    @StringRes val label: Int,
    val file: File?
)