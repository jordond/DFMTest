package com.worldturtlemedia.photos.menu

import androidx.fragment.app.activityViewModels
import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.binder
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.ktx.visibleOrGone
import com.worldturtlemedia.photos.auth.PhotosAuthModel
import com.worldturtlemedia.photos.databinding.FragmentMenuBinding

class MenuFragment : BindingFragment<FragmentMenuBinding>() {

    override val bindingInflater = binder { FragmentMenuBinding.inflate(it) }

    private val authViewModel: PhotosAuthModel by activityViewModels()

    override fun observeViewModel() {
        authViewModel.state.observe(viewLifecycleOwner) { state ->
            with(binding) {
                groupAuth.visibleOrGone = state.isAuthenticated
                groupNoAuth.visibleOrGone = !state.isAuthenticated
            }
        }
    }
}