package com.worldturtlemedia.dfmtest.audiobase.assets

import com.github.ajalt.timberkt.i
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * 1. Check if module is loaded
 *  1a. If not load Module
 * 2. Check if audio files exist
 *  2a. If not unzip to folder
 * 3.
 *
 */

suspend fun unzip(
    inputStream: InputStream,
    targetDir: File,
    overwrite: Boolean = true,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    onExtracted: (entry: ZipEntry) -> Unit = {}
) = withContext(dispatcher) {
    ZipInputStream(inputStream).use { zipStream ->
        zipStream.forEachEntry { entry ->
            i { "Extracting: ${entry.name}" }

            // Ensure output folder exists
            val outputFile = File(targetDir, entry.name)
            with(if (entry.isDirectory) outputFile else outputFile.parentFile) {
                if (!isDirectory && !mkdirs())
                    throw FileNotFoundException("Failed to ensure directory $absolutePath")
            }

            // Don't need to extract folders
            if (entry.isDirectory) return@forEachEntry

            if (!overwrite && outputFile.exists()) {
                i { "Skipping ${entry.name}" }
                return@forEachEntry
            }

            outputFile.outputStream().use { output ->
                i { "Copying ${entry.name} to ${outputFile.absolutePath}" }
                zipStream.copyTo(output)
                withContext(Dispatchers.Main) { onExtracted(entry) }
            }
        }
    }
}

private suspend fun ZipInputStream.forEachEntry(block: suspend (entry: ZipEntry) -> Unit) {
    var entry: ZipEntry? = null
    while ({ entry = nextEntry; entry }() != null) {
        try {
            block(entry as ZipEntry)
        } finally {
            closeEntry()
        }
    }
}
