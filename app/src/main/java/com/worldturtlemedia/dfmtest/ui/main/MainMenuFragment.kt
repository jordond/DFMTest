package com.worldturtlemedia.dfmtest.ui.main

import com.worldturtlemedia.dfmtest.common.base.navigate
import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.binder
import com.worldturtlemedia.dfmtest.common.ktx.onClick
import com.worldturtlemedia.dfmtest.common.ktx.startActivity
import com.worldturtlemedia.dfmtest.databinding.FragmentMainMenuBinding
import com.worldturtlemedia.photos.GooglePhotosActivity

class MainMenuFragment : BindingFragment<FragmentMainMenuBinding>() {

    override val bindingInflater = binder { FragmentMainMenuBinding.inflate(it) }

    override fun setupViews() = with(binding) {
        btnDFM.onClick { navigate(MainMenuFragmentDirections.toDFMMenu()) }
        btnGooglePhotos.onClick {
            requireContext().startActivity<GooglePhotosActivity>()
        }
    }
}