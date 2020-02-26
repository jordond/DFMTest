package com.worldturtlemedia.dfmtest.ui.main

import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.navigate
import com.worldturtlemedia.dfmtest.common.base.viewbinding.LayoutFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.dfmtest.common.ktx.onClick
import com.worldturtlemedia.dfmtest.common.ktx.startActivity
import com.worldturtlemedia.dfmtest.databinding.FragmentMainMenuBinding
import com.worldturtlemedia.photos.GooglePhotosActivity

class MainMenuFragment : LayoutFragment(R.layout.fragment_main_menu) {

    private val binding by viewBinding(FragmentMainMenuBinding::bind)

    override fun setupViews() = with(binding) {
        btnDFM.onClick { navigate(MainMenuFragmentDirections.toDFMMenu()) }
        btnGooglePhotos.onClick {
            requireContext().startActivity<GooglePhotosActivity>()
        }
    }
}