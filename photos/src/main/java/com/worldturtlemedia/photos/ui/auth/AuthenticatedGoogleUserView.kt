package com.worldturtlemedia.photos.ui.auth

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.android.material.button.MaterialButton
import com.mikhaellopez.circularimageview.CircularImageView
import com.worldturtlemedia.dfmtest.common.ktx.bind
import com.worldturtlemedia.dfmtest.common.ktx.onClick
import com.worldturtlemedia.photos.R

class AuthenticatedGoogleUserView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    init {
        inflate(context, R.layout.view_authenticated_google_user, this)
    }

    private val txtAuthEmail: AppCompatTextView by bind(R.id.txtAuthEmail)
    private val btnDisconnect: MaterialButton by bind(R.id.btnDisconnect)
    private val imgAvatar: CircularImageView by bind(R.id.imgAvatar)

    private var onDisconnectClickListener: () -> Unit = {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        btnDisconnect.onClick { onDisconnectClickListener() }
    }

    fun updateInfo(info: GoogleAuthUser) = with(info) {
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

data class GoogleAuthUser(
    val email: String,
    val avatarUrl: String
)