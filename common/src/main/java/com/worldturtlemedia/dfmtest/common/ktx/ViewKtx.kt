package com.worldturtlemedia.dfmtest.common.ktx

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun <T : View> Activity.bind(@IdRes res: Int): Lazy<T> = unsafeLazy { findViewById<T>(res) }

fun <T : View> Fragment.bind(@IdRes res: Int): Lazy<T> = unsafeLazy {
    view?.findViewById<T>(res)
        ?: throw IllegalAccessException("Unable to bind view")
}

fun <T : View> View.bind(@IdRes res: Int): Lazy<T> = unsafeLazy {
    findViewById<T>(res)
        ?: throw IllegalAccessException("Unable to bind view")
}

private fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)