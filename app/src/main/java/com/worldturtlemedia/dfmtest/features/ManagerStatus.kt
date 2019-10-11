package com.worldturtlemedia.dfmtest.features

import android.content.IntentSender
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.worldturtlemedia.dfmtest.common.view.LoadingProgress

sealed class ManagerStatus {
    data class Downloading(
        val feature: Feature,
        val progress: LoadingProgress
    ) : ManagerStatus()

    data class RequiresConfirmation(val intentSender: IntentSender?) : ManagerStatus()
    data class Installing(
        val feature: Feature,
        val progress: LoadingProgress
    ) : ManagerStatus()

    data class Installed(val feature: Feature) : ManagerStatus()
    data class Error(val feature: Feature, val code: Int) : ManagerStatus()
}

fun SplitInstallSessionState.asLoadingProgress() = LoadingProgress(
    total = totalBytesToDownload().toInt(),
    current = bytesDownloaded().toInt()
)
