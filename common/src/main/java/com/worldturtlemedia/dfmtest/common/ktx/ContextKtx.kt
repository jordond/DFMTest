package com.worldturtlemedia.dfmtest.common.ktx

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import java.io.File

fun Context.color(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun Context.getRawUri(@RawRes res: Int): Uri? {
    val root = "${ContentResolver.SCHEME_ANDROID_RESOURCE}${File.pathSeparator}"
    val resourcePath = "$packageName${File.separator}$res"
    val path = "$root${File.separator}$resourcePath"

    return try {
        i { "Parsing $path" }
        Uri.parse(path)
    } catch (error: Throwable) {
        e(error) { "Failed to parse $path" }
        null
    }
}