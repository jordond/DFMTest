package com.worldturtlemedia.photos.auth.ui

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.widget.FrameLayout
import coil.api.load
import coil.transform.CircleCropTransformation
import com.worldturtlemedia.dfmtest.common.ktx.onClick
import com.worldturtlemedia.photos.R
import kotlinx.android.synthetic.main.view_authenticated_google_user.view.*

class AuthenticatedGoogleUserView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    init {
        inflate(context, R.layout.view_authenticated_google_user, this)
    }

    private var onDisconnectClickListener: () -> Unit = {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        btnDisconnect.onClick { onDisconnectClickListener() }
    }

    fun updateInfo(
        email: String,
        avatarUrl: Uri?
    ) = updateInfo(AuthenticatedGoogleUserState(email, avatarUrl))

    fun updateInfo(info: AuthenticatedGoogleUserState) = with(info) {
        txtAuthEmail.text = context.getString(R.string.google_auth_email_display, email)
        imgAvatar.load(avatarUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_person)
            transformations(CircleCropTransformation())
        }
    }

    fun setOnDisconnect(block: () -> Unit) {
        onDisconnectClickListener = block
    }
}

data class AuthenticatedGoogleUserState(
    val email: String,
    val avatarUrl: Uri?
)