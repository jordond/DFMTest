package com.worldturtlemedia.dfmtest.ui.main

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.base.navigate
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.view.LoadingProgress
import com.worldturtlemedia.dfmtest.features.Feature
import com.worldturtlemedia.dfmtest.features.FeatureManagerModel
import com.worldturtlemedia.dfmtest.features.display
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : BaseFragment() {

    override fun layout(): Int = R.layout.main_fragment

    private val featureManagerModel: FeatureManagerModel by activityViewModels()
    private val viewModel: MainViewModel by viewModels()

    override fun setupViews() {
        btnAudioPicker.setOnClickListener {
            navigate(MainFragmentDirections.toAudioPicker())
        }

        btnAudioPlayer.setOnClickListener {
            navigate(MainFragmentDirections.toAudioPlayer())
        }

        btnToggleLoading.setOnClickListener {
            loadingView.setLoading(true, "Debug loading...")

            viewLifecycleOwner.lifecycleScope.launch {
                val total = 100_000
                for (current in 0 .. total step 200) {
                    delay(10)
                    loadingView.setProgress(LoadingProgress(total, current))
                }
            }
        }

        txtAvailableFeatures.text = getString(R.string.available_features, Feature.all.display)
        txtLoadedFeaturesLabel.text = getString(R.string.loaded_features, getString(R.string.none))
    }

    override fun subscribeViewModel() {
        featureManagerModel.installedFeatures.observe(owner) { features ->
            val string = if (features.isEmpty()) getString(R.string.none) else features.display
            txtLoadedFeaturesLabel.text = getString(R.string.loaded_features, string)
        }
    }
}
