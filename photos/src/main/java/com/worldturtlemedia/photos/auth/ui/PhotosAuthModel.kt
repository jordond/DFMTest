package com.worldturtlemedia.photos.auth.ui

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.worldturtlemedia.dfmtest.common.base.viewmodel.State
import com.worldturtlemedia.dfmtest.common.base.viewmodel.StateViewModel
import com.worldturtlemedia.photos.auth.data.GoogleAuthRepoFactory
import com.worldturtlemedia.photos.auth.data.GoogleAuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosAuthModel : StateViewModel<PhotosAuthState>(PhotosAuthState()) {

    private val googleAuthRepo = GoogleAuthRepoFactory.instance

    init {
        addStateSource(googleAuthRepo.state.asLiveData()) { auth ->
            copy(auth = auth)
        }
    }

    fun init(context: Context) {
        googleAuthRepo.refreshAuthState(context)
    }

    fun signIn(activity: FragmentActivity) = launchMain { googleAuthRepo.signIn(activity) }

    fun signOut() = launchMain { googleAuthRepo.signOut() }

    private fun launchMain(
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(Dispatchers.Main, block = block)
}

data class PhotosAuthState(
    val auth: GoogleAuthState = GoogleAuthState.Unauthenticated
) : State {

    val isAuthenticated: Boolean
        get() = auth is GoogleAuthState.Authenticated
}