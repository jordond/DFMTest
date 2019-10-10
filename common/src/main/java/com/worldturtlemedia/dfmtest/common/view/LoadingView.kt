package com.worldturtlemedia.dfmtest.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewPropertyAnimator
import android.widget.LinearLayout
import com.worldturtlemedia.dfmtest.common.R

class LoadingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    init {
        inflate(context, R.layout.loading_view, this)
    }

    private var animator: ViewPropertyAnimator? = null

    var duration: Long = DEFAULT_ANIMATION_MS

    var isLoading: Boolean = false
        set(value) {
            field = value
            setVisibility(value)
        }

    fun resetDuration() {
        duration = DEFAULT_ANIMATION_MS
    }

    private fun setVisibility(isLoading: Boolean) {
        val alpha = if (isLoading) 1.0f else 0f

        animator?.cancel()
        animator = animate().alpha(alpha).setDuration(duration).also { it.start() }
    }

    companion object {

        const val DEFAULT_ANIMATION_MS = 1000L
    }
}