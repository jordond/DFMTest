package com.worldturtlemedia.dfmtest.ui.main

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.base.navigate
import com.worldturtlemedia.dfmtest.common.features.*
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.ktx.visibleOrGone
import com.worldturtlemedia.dfmtest.common.view.LoadingProgress
import kotlinx.android.synthetic.main.fragment_dfm_menu.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DFMMenuFragment : BaseFragment() {

    override fun layout(): Int = R.layout.fragment_dfm_menu

    private val featureManagerModel: FeatureManagerModel by activityViewModels()

    override fun setupViews() {
        btnAudioPicker.visibleOrGone = Feature.available.contains(Feature.AudioFull)

        btnAudioPicker.setOnClickListener {
            navigate(DFMMenuFragmentDirections.toAudioPicker())
        }

        btnAudioPlayer.setOnClickListener {
            navigate(DFMMenuFragmentDirections.toAudioPlayer())
        }

        btnTestFeatureInstall.setOnClickListener {
            owner.lifecycleScope.launch {
                FeatureManager.instance.runWithFeature(
                    feature = Feature.Test,
                    onFailure = { makeToast("Install failed") },
                    onSuccess = { makeToast("Install success!") }
                )
            }
        }

        btnTestFeatureUninstall.setOnClickListener {
            owner.lifecycleScope.launch {
                val uninstalled = FeatureManager.instance.uninstall(Feature.Test)
                makeToast(if (uninstalled) "Uninstalled!" else "Failed...")
            }
        }

        btnInstallAudioAssets.setOnClickListener {
            owner.lifecycleScope.launch {
                FeatureManager.instance.runWithFeature(
                    feature = Feature.AudioRaw,
                    onFailure = { makeToast("Install failed") },
                    onSuccess = { makeToast("Install started!") }
                )
            }
        }

        btnUnInstallAudioAssets.setOnClickListener {
            owner.lifecycleScope.launch {
                val uninstalled = FeatureManager.instance.uninstall(Feature.AudioRaw)
                makeToast(if (uninstalled) "Uninstalled Assets!" else "Failed...")
            }
        }

        btnToggleLoading.setOnClickListener {
            loadingView.setLoading(true, "Debug loading...")

            viewLifecycleOwner.lifecycleScope.launch {
                val total = 100_000
                for (current in 0..total step 200) {
                    delay(10)
                    loadingView.setProgress(LoadingProgress(total, current))
                }
            }
        }

        txtAvailableFeatures.text = getString(R.string.available_features, Feature.available.display)
        txtLoadedFeaturesLabel.text = getString(R.string.loaded_features, getString(R.string.none))
    }

    override fun observeViewModel() {
        featureManagerModel.manager.installedFeatures.observe(owner) { features ->
            val string = if (features.isEmpty()) getString(R.string.none) else features.display
            txtLoadedFeaturesLabel.text = getString(R.string.loaded_features, string)
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
