package com.worldturtlemedia.photos.photos

import com.google.android.gms.common.api.Scope

class GooglePhotosRepo {

    companion object {

        val SCOPE_PHOTOS_READONLY = Scope("https://www.googleapis.com/auth/photoslibrary.readonly")
    }
}