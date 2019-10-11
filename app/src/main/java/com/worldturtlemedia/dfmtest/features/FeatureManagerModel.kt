package com.worldturtlemedia.dfmtest.features

import android.content.Context
import androidx.lifecycle.*
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.*
import com.worldturtlemedia.dfmtest.common.ktx.mutableLiveDataOf

class FeatureManagerModel : ViewModel() {

    private var splitInstallManager: SplitInstallManager? = null
    private val manager: SplitInstallManager
        get() = splitInstallManager ?: throw IllegalStateException("Did you forget to call `init()`?")

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
            INSTALLED -> emit(ManagerStatus.Installed(feature))
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
        splitInstallManager = SplitInstallManagerFactory.create(context).apply {
            registerListener(listener)
        }
    }

    fun install(feature: Feature) {
        if (manager.installedModules.contains(feature.name)) {
            i { "Feature ${feature.name} is already installed." }
            return emit(ManagerStatus.Installed(feature))
        }

        val request = SplitInstallRequest.newBuilder().addModule(feature.name).build()
        i { "Attempting to install $feature"}

        manager.startInstall(request)
    }

    private fun emit(status: ManagerStatus) {
        i { "Emitting status: $status" }
        _status.value = status
    }

    override fun onCleared() {
        super.onCleared()
        i { "Unregistering SplitInstallManager" }
        manager.unregisterListener(listener)
    }
}
