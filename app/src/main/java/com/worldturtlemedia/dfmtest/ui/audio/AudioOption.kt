package com.worldturtlemedia.dfmtest.ui.audio

import androidx.annotation.StringRes
import java.io.File

data class AudioOption(
    @StringRes val label: Int,
    val path: File
)

