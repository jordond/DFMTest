package com.worldturtlemedia.dfmtest.audiobase.models

import androidx.annotation.StringRes
import java.io.File

data class AudioFileOption(
    @StringRes val label: Int,
    val file: File?
)