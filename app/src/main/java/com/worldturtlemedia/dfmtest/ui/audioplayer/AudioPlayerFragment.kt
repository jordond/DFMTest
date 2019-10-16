package com.worldturtlemedia.dfmtest.ui.audioplayer

import android.app.Activity
import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.audiobase.assets.AudioAssetManager.Companion.AUDIO_ASSETS_FOLDER
import com.worldturtlemedia.dfmtest.audiobase.assets.AudioAssetManager.Companion.RAW_ASSETS_FILENAME
import com.worldturtlemedia.dfmtest.audiobase.assets.AudioAssetManager.Companion.assetPath
import com.worldturtlemedia.dfmtest.audiobase.assets.unzip
import com.worldturtlemedia.dfmtest.common.base.BaseFragment
import com.worldturtlemedia.dfmtest.common.ktx.cast
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.ui.main.MainActivity
import kotlinx.android.synthetic.main.audio_picker_fragment.bar
import kotlinx.android.synthetic.main.audio_picker_fragment.fab
import kotlinx.android.synthetic.main.audio_player_fragment.*
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream

class AudioPlayerFragment : BaseFragment() {

    override fun layout(): Int = R.layout.audio_player_fragment

    private val viewModel: AudioPlayerViewModel by viewModels()

    override fun setupViews() {
        // Ideally we would setup the bottom bar with the Navigation component
        // But we just need a back button, so we can easily implement it.
        bar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        fab.setOnClickListener {

        }

        btnLoad.setOnClickListener {
            with(requireActivity()) {
                val stream: InputStream =
                    createPackageContext(packageName, 0).assets.open(RAW_ASSETS_FILENAME)

                // TODO: Implement a proper loading view
                viewLifecycleOwner.lifecycleScope.launch {
                    val act = requireActivity().cast<MainActivity>()
                    act?.setLoading(true)

                    unzip(stream, assetPath(requireContext()), overwrite = false) { name ->
                        act?.setLoading(true, "Unzipped ${name.name}")
                    }

                    act?.setLoading(false)
                }
            }
        }
    }

    override fun subscribeViewModel() {
        viewModel.state.observe(owner) { state ->

        }
    }
}