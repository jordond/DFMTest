package com.worldturtlemedia.photos.ui.menu

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.github.florent37.inlineactivityresult.kotlin.coroutines.startForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.binder
import com.worldturtlemedia.dfmtest.common.ktx.observe
import com.worldturtlemedia.dfmtest.common.ktx.onClick
import com.worldturtlemedia.dfmtest.common.ktx.visibleOrGone
import com.worldturtlemedia.photos.databinding.FragmentMenuBinding
import com.worldturtlemedia.photos.ui.auth.PhotosAuthModel
import kotlinx.coroutines.launch

class MenuFragment : BindingFragment<FragmentMenuBinding>() {

    override val bindingInflater = binder { FragmentMenuBinding.inflate(it) }

    private val authViewModel: PhotosAuthModel by activityViewModels()

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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestScopes(Scope("https://www.googleapis.com/auth/photoslibrary.readonly"))
            .requestServerAuthCode("421255191555-32svvvtis87a7k099j61okpbshla8tni.apps.googleusercontent.com")
            .build()

        val client = GoogleSignIn.getClient(requireActivity(), gso)
        val intent = client.signInIntent
        startActivityForResult(intent, 42)
//        val resultData = startForResult(intent).data
//        val resultTask = GoogleSignIn.getSignedInAccountFromIntent(resultData)
//        if (resultTask.isSuccessful) {
//            val account: GoogleSignInAccount = resultTask.result!!
//            e { "account: $account" }
//        } else {
//            e(resultTask.exception) { "FAILED" }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 42) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task?.getResult(ApiException::class.java)
            val exception = task?.exception
            e { "$account" }
        }
    }
}