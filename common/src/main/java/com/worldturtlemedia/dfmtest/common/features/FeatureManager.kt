package com.worldturtlemedia.dfmtest.common.features

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.*
import com.worldturtlemedia.dfmtest.common.ktx.mutableLiveDataOf
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FeatureManager {

    companion object {
        lateinit var instance: FeatureManager
            private set

        @Deprecated("Experimental")
        // TODO: This type of functionality needs to be further investigated
        // It can be called before the [instance] is initialized.
        fun has(feature: Feature) = instance.manager.installedFeatures.contains(feature)

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

    /**
     * This function will install the module asynchronously.
     *
     * The coroutine will resume in the `addOnSuccessListener` HOWEVER that does not mean
     * that the feature is installed.  It just means that the services has queued it for installing.
     *
     * Instead you need to listen to the updates made by [listener], which are stored in [status].
     */
    suspend fun installAsync(feature: Feature) {
        if (manager.installedModules.contains(feature.name)) {
            i { "Feature $feature is already installed." }
            return
        }

        val request = SplitInstallRequest.newBuilder().addModule(feature.name).build()
        i { "Attempting to install $feature" }

        return suspendCancellableCoroutine { continuation ->
            manager.startInstall(request)
                .addOnSuccessListener {
                    _installed.postValue(manager.installedFeatures)
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it.cause ?: Throwable("Install failed!"))
                }
        }
    }

    suspend fun install(feature: Feature): ManagerStatus {
        if (manager.installedModules.contains(feature.name)) {
            i { "Feature $feature is already installed." }
            return ManagerStatus.Installed(feature)
        }

        val request = SplitInstallRequest.newBuilder().addModule(feature.name).build()
        return suspendCancellableCoroutine { continuation ->
            val installListener = object : SplitInstallStateUpdatedListener {
                override fun onStateUpdate(state: SplitInstallSessionState) {
                    when (state.status()) {
                        INSTALLED -> {
                            manager.unregisterListener(this)
                            continuation.resume(ManagerStatus.Installed(feature))
                        }
                        FAILED -> {
                            manager.unregisterListener(this)
                            continuation.resume(ManagerStatus.Error(feature, state.errorCode()))
                        }
                        CANCELED -> {
                            manager.unregisterListener(this)
                            continuation.resume(ManagerStatus.Cancelled(feature))
                        }
                    }
                }
            }

            manager.registerListener(installListener)
            manager.startInstall(request)
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
                .addOnSuccessListener {
                    _installed.postValue(manager.installedFeatures)
                    continuation.resume(true)
                }
                .addOnFailureListener { continuation.resume(false) }
        }
    }

    private fun emit(status: ManagerStatus) {
        i { "Emitting status: $status" }
        _status.value = status
    }
}

/**
 * TODO
 *
 * This is just a quick implementation of running a block of code with a specific feature.
 *
 * It needs to be tweaked to handle the different states.  Right now it can only handle Installed,
 * and Failed.  But the user could also cancel the install request.
 */
suspend fun <T> FeatureManager.runWithFeature(
    feature: Feature,
    onFailure: ((Throwable) -> Unit)? = null,
    onSuccess: suspend () -> T
): T? {
    try {
        // TODO: Need to handle the different results, maybe rethink the whole inline approach.
        return when (val result = install(feature)) {
            is ManagerStatus.Installed -> onSuccess()
            is ManagerStatus.Cancelled -> null
            is ManagerStatus.Error -> throw Throwable("Failed to install: ${result.code}")
            else -> throw IllegalArgumentException("Unsupported result: $result")
        }
    } catch (error: Throwable) {
        e(error) { "Unable to install $feature!" }
        onFailure?.invoke(error) ?: throw error
    }

    return null
}

suspend fun <T> FeatureManager.runWithFeature(
    feature: Feature,
    onSuccess: suspend () -> T
): T = runWithFeature(feature, onFailure = { throw it }, onSuccess = onSuccess)
    ?: throw IllegalStateException("Unable to install $feature")

val SplitInstallManager.installedFeatures: Features
    get() = installedModules.map { Feature.of(it) }