package com.worldturtlemedia.photos.ui.auth

import com.worldturtlemedia.dfmtest.common.base.viewmodel.State
import com.worldturtlemedia.dfmtest.common.base.viewmodel.StateViewModel

class PhotosAuthModel : StateViewModel<PhotosAuthState>(
    PhotosAuthState()
) {


}

data class PhotosAuthState(
    val isAuthenticated: Boolean = false
) : State