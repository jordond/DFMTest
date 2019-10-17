package com.worldturtlemedia.dfmtest.common.features

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.i

class FeatureManagerModel : ViewModel() {

    lateinit var manager: FeatureManager
        private set

    fun init(context: Context) {
        manager = FeatureManager.init(context)
        manager.registerListener()
    }

    override fun onCleared() {
        super.onCleared()
        i { "Unregistering SplitInstallManager" }
        manager.unregisterListener()
    }
}
