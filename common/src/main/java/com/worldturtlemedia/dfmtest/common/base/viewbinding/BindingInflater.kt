package com.worldturtlemedia.dfmtest.common.base.viewbinding

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

typealias BindingInflater<T> = (LayoutInflater) -> T

fun <T : ViewBinding> binder(
    block: (inflater: LayoutInflater) -> T
): BindingInflater<T> = { inflater -> block(inflater) }