package com.worldturtlemedia.dfmtest.common.ktx

import androidx.lifecycle.*

fun <T> liveDataOf(data: T? = null): LiveData<T> =
    object : LiveData<T>() {
        init {
            value = data
        }
    }

fun <T> mutableLiveDataOf(data: T? = null): MutableLiveData<T> =
    MutableLiveData<T>().apply { value = data }

fun <T, R> LiveData<T>.observeProperty(
    owner: LifecycleOwner,
    property: (T) -> R,
    onChange: (value: R) -> Unit
) {
    map(property).distinctUntilChanged().observe(owner, onChange)
}