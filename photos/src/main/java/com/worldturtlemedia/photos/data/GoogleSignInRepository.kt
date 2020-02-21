package com.worldturtlemedia.photos.data

import android.content.Context
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope

class GoogleSignInRepository(
    private val context: Context
) {

    fun test() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope("https://www.googleapis.com/auth/photoslibrary.readonly"))
            .build()

        val client = GoogleSignIn.getClient(context, gso)
        val apiClient = GoogleApiClient.Builder(context).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
    }

}