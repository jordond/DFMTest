package com.worldturtlemedia.dfmtest.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import com.worldturtlemedia.dfmtest.common.R
import com.worldturtlemedia.dfmtest.common.ktx.bind
import com.worldturtlemedia.dfmtest.common.ktx.dp
import com.worldturtlemedia.dfmtest.common.ktx.visible
import com.worldturtlemedia.dfmtest.common.ktx.visibleOrGone

class LoadingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    init {
        inflate(context, R.layout.loading_view, this)
    }

    private val layout: FrameLayout by bind(R.id.progressLayout)
    private val progressBar: ProgressBar by bind(R.id.progressBar)
    private val textView: AppCompatTextView by bind(R.id.txtLoadingText)
    private val textProgress: AppCompatTextView by bind(R.id.txtProgress)

    private var animator: ViewPropertyAnimator? = null
    var isLoading: Boolean = false
        private set

    override fun onFinishInflate() {
        super.onFinishInflate()

        elevation = 99f
        visible = false
    }

    fun setLoading(loading: Boolean, message: String? = null) {
        if (textView.text != message) {
            textView.text = message ?: "Loading..."
        }

        if (loading == isLoading) return

        isLoading = loading
        setVisibility(isLoading)

        if (!isLoading) {
            with(progressBar) {
                max = 0
                progress = 0
            }
            textProgress.visible = false
        }
    }

    fun setProgress(loadingProgress: LoadingProgress, handleFinished: Boolean = true) {
        val (total, current) = loadingProgress
        with(progressBar) {
            max = total
            progress = current
        }

        with (textProgress) {
            text = "$current/$total"
            visible = true
        }

        if (handleFinished && current >= total) {
            setLoading(false)
        }
    }

    private fun setVisibility(isLoading: Boolean) {
        val alpha = if (isLoading) 1.0f else 0f

        visibleOrGone = isLoading

        animator?.cancel()
        animator =
            layout.animate().alpha(alpha).setDuration(DEFAULT_ANIMATION_MS).also { it.start() }
    }

    companion object {

        const val DEFAULT_ANIMATION_MS = 300L
    }
}