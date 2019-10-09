package com.worldturtlemedia.dfmtest.common.ktx

import androidx.lifecycle.*

fun <T, R> LiveData<T>.observeProperty(owner: LifecycleOwner, property: (T) -> R, onChange: (value: R) -> Unit) {
    map(property).distinctUntilChanged().observe(owner, onChange)
}