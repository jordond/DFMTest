package com.worldturtlemedia.photos.menu

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.worldturtlemedia.dfmtest.common.base.navigate
import com.worldturtlemedia.dfmtest.common.base.viewbinding.LayoutFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.dfmtest.common.ktx.onClick
import com.worldturtlemedia.dfmtest.common.ktx.visibleOrGone
import com.worldturtlemedia.photos.R
import com.worldturtlemedia.photos.auth.data.GoogleAuthState
import com.worldturtlemedia.photos.auth.ui.PhotosAuthModel
import com.worldturtlemedia.photos.databinding.FragmentMenuBinding

class MenuFragment : LayoutFragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::bind)

    private val authViewModel: PhotosAuthModel by activityViewModels()

    override fun setupViews() = with(binding) {
        btnSignIn.onClick { authViewModel.signIn(requireActivity()) }

        authUserView.setOnDisconnect { authViewModel.signOut() }
        btnAlbums.onClick { navigate(MenuFragmentDirections.toAlbums()) }
        btnDate.onClick { navigate(MenuFragmentDirections.toDatePicker()) }
    }

    override fun observeViewModel() {
        authViewModel.init(requireContext())

        authViewModel.state.observe(owner) { state ->
            with(binding) {
                groupAuth.visibleOrGone = state.isAuthenticated
                groupNoAuth.visibleOrGone = !state.isAuthenticated
                txtAuthError.visibleOrGone = state.auth is GoogleAuthState.Error
            }

            if (state.auth is GoogleAuthState.Error) {
                @SuppressLint("SetTextI18n")
                binding.txtAuthError.text = "Error: ${state.auth.exception.localizedMessage}"
            }

            if (state.auth is GoogleAuthState.Authenticated) {
                val authUser = state.auth.user
                binding.authUserView.updateInfo(authUser.email, authUser.avatarUrl)
            }
        }
    }
}