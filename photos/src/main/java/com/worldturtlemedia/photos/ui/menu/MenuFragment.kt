package com.worldturtlemedia.photos.ui.menu

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.github.ajalt.timberkt.e
import com.github.florent37.inlineactivityresult.kotlin.coroutines.startForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.worldturtlemedia.dfmtest.common.base.viewbinding.BasicFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.dfmtest.common.ktx.gone
import com.worldturtlemedia.dfmtest.common.ktx.onClick
import com.worldturtlemedia.dfmtest.common.ktx.visible
import com.worldturtlemedia.dfmtest.common.ktx.visibleOrGone
import com.worldturtlemedia.photos.R
import com.worldturtlemedia.photos.databinding.FragmentMenuBinding
import com.worldturtlemedia.photos.ui.auth.GoogleAuthUser
import com.worldturtlemedia.photos.ui.auth.PhotosAuthModel
import kotlinx.coroutines.launch

class MenuFragment : BasicFragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::bind)

    private val authViewModel: PhotosAuthModel by activityViewModels()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestProfile()
        .requestScopes(Scope("https://www.googleapis.com/auth/photoslibrary.readonly"))
        .requestServerAuthCode("421255191555-32svvvtis87a7k099j61okpbshla8tni.apps.googleusercontent.com")
        .build()

    val client by lazy { GoogleSignIn.getClient(requireActivity(), gso) }

    override fun setupViews() {

        binding.btnSignIn.onClick {
            lifecycleScope.launch {
                try {
                    test()
                } catch (error: Throwable) {
                    e(error) { "shit went wrong" }
                    throw error
                }
            }
        }

        binding.authUserView.setOnDisconnect {
            client.signOut()
        }
    }

    override fun observeViewModel() {
        authViewModel.state.observe(owner) { state ->
            with(binding) {
                groupAuth.visibleOrGone = state.isAuthenticated
                groupNoAuth.visibleOrGone = !state.isAuthenticated
            }
        }
    }

    suspend fun test() {

        val intent = client.signInIntent
        val testtt = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (testtt != null) {
            e { "Do nothing: ${testtt.email}" }
            client.signOut()
            return
        }
        val resultData = startForResult(intent).data
        val resultTask = GoogleSignIn.getSignedInAccountFromIntent(resultData)
        if (resultTask.isSuccessful) {
            val account: GoogleSignInAccount = resultTask.result!!

            binding.groupNoAuth.gone = true
            binding.groupAuth.visible = true
            binding.authUserView.updateInfo(GoogleAuthUser(account.email!!, account.photoUrl))

            e { "account: $account" }
        } else {
            e(resultTask.exception) { "FAILED" }
        }
    }
}