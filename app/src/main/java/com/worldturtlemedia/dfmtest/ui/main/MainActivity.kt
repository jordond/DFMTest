package com.worldturtlemedia.dfmtest.ui.main

import androidx.activity.viewModels
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseActivity
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.view.LoadingProgress
import com.worldturtlemedia.dfmtest.features.Feature
import com.worldturtlemedia.dfmtest.features.FeatureManagerModel
import com.worldturtlemedia.dfmtest.features.ManagerStatus
import com.worldturtlemedia.dfmtest.features.ManagerStatus.*
import com.worldturtlemedia.dfmtest.features.display
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.loadingView
import kotlinx.android.synthetic.main.main_fragment.*

class MainActivity : BaseActivity() {

    override fun layout(): Int = R.layout.main_activity

    private val featureMangerModel: FeatureManagerModel by viewModels()

    override fun afterCreate() {
        featureMangerModel.init(this)

        featureMangerModel.status.observe(this) { status ->
            when (status) {
                is Downloading -> updateLoading("Downloading", status.feature, status.progress)
                is Installing -> updateLoading("Installing", status.feature, status.progress)
                is RequiresConfirmation -> {
                    startIntentSender(status.intentSender, null, 0, 0, 0)
                }
                else -> loadingView.setLoading(false)
            }
        }

        featureMangerModel.installedFeatures.observe(this) { installed ->
            txtLoadedFeaturesLabel.text = getString(R.string.loaded_features, installed.display)
        }
    }

    private fun updateLoading(message: String, feature: Feature, progress: LoadingProgress) {
        with (loadingView) {
            setLoading(true, "$message ${feature.name}...")
            setProgress(progress)
        }
    }
}
