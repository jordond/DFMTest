package com.worldturtlemedia.dfmtest.audiobase.models

import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.worldturtlemedia.dfmtest.common.ktx.getRawUri

data class AudioOption(
    @StringRes val label: Int,
    @RawRes val rawRes: Int?
)

fun AudioOption.filePath(context: Context): Uri? = rawRes?.let { resource ->
    context.applicationContext.getRawUri(resource)
}