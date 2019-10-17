package com.worldturtlemedia.dfmtest.common.features

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.*
import com.worldturtlemedia.dfmtest.common.ktx.mutableLiveDataOf
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FeatureManager {

    companion object {
        lateinit var instance: FeatureManager
            private set

        fun init(context: Context) = FeatureManager().also { manager ->
            instance = manager
            instance.init(context)
        }
    }

    private var splitInstallManager: SplitInstallManager? = null
    private val manager: SplitInstallManager
        get() = splitInstallManager
            ?: throw IllegalStateException("Did you forget to call `init()`?")

    private val _installed = mutableLiveDataOf<Features>(emptyList())
    val installedFeatures: LiveData<List<Feature>>
        get() = _installed.distinctUntilChanged()

    private val listener = SplitInstallStateUpdatedListener { state ->
        val feature = state.moduleNames().first().let { Feature.of(it) }
        when (state.status()) {
            DOWNLOADING -> emit(ManagerStatus.Downloading(feature, state.asLoadingProgress()))
            REQUIRES_USER_CONFIRMATION -> emit(
                ManagerStatus.RequiresConfirmation(state.resolutionIntent()?.intentSender)
            )
            INSTALLING -> emit(ManagerStatus.Installing(feature, state.asLoadingProgress()))
            INSTALLED -> {
                emit(ManagerStatus.Installed(feature))
                _installed.value = manager.installedFeatures
            }
            FAILED -> {
                e { "Unable to install $feature" }
                emit(ManagerStatus.Error(feature, state.errorCode()))
            }
        }
    }

    private val _status = mutableLiveDataOf<ManagerStatus>()
    val status: LiveData<ManagerStatus> = _status.distinctUntilChanged()

    fun init(context: Context) {
        if (splitInstallManager != null) {
            manager.unregisterListener(listener)
        }

        i { "Initializing SplitInstallManager" }
        splitInstallManager = SplitInstallManagerFactory.create(context)
        _installed.value = manager.installedFeatures
    }

    fun registerListener() {
        unregisterListener()
        splitInstallManager?.registerListener(listener)
    }

    fun unregisterListener() {
        splitInstallManager?.unregisterListener(listener)
    }

    suspend fun install(feature: Feature) {
        if (manager.installedModules.contains(feature.name)) {
            i { "Feature $feature is already installed." }
            return
        }

        val request = SplitInstallRequest.newBuilder().addModule(feature.name).build()
        i { "Attempting to install $feature" }

        return suspendCancellableCoroutine { continuation ->
            manager.startInstall(request)
                .addOnSuccessListener { continuation.resume(Unit) }
                .addOnFailureListener { continuation.cancel(it.cause) }
        }
    }

    suspend fun uninstall(vararg feature: Feature): Boolean {
        i { "Uninstalling $feature" }

        val features = feature.toList()
            .map { it.name }
            .filter { manager.installedModules.contains(it) }

        if (features.isEmpty()) {
            i { "No features to uninstall!" }
            return true
        }

        return suspendCancellableCoroutine { continuation ->
            manager.deferredUninstall(features)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }
    }

    private fun emit(status: ManagerStatus) {
        i { "Emitting status: $status" }
        _status.value = status
    }
}

suspend inline fun <T> FeatureManager.runWithFeature(
    feature: Feature,
    noinline onFailure: ((SplitInstallException) -> Unit)? = null,
    onSuccess: () -> T
): T? {
    try {
        install(feature)
        return onSuccess()
    } catch (error: SplitInstallException) {
        e(error) { "Unable to install $feature!" }
        onFailure?.invoke(error) ?: throw error
    }

    return null
}

suspend inline fun <T> FeatureManager.runWithFeature(
    feature: Feature,
    onSuccess: () -> T
): T = runWithFeature(feature, onFailure = { throw it }, onSuccess = onSuccess)
    ?: throw IllegalStateException("Unable to install $feature")

val SplitInstallManager.installedFeatures: Features
    get() = installedModules.map { Feature.of(it) }