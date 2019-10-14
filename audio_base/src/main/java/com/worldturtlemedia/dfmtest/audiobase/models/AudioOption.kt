package com.worldturtlemedia.dfmtest.audiobase.models

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class AudioOption(
    @StringRes val label: Int,
    @RawRes val rawRes: Int?
)
