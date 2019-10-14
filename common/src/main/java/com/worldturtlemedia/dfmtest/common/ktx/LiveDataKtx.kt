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

fun <T> mediatorLiveDataOf(data: T? = null): MediatorLiveData<T> =
    MediatorLiveData<T>().apply { value = data }

fun <T, R> LiveData<T>.observeProperty(
    owner: LifecycleOwner,
    property: (T) -> R,
    onChange: (value: R) -> Unit
) {
    map(property).distinctUntilChanged().observe(owner, onChange)
}

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    observe(owner, Observer { it?.let(observer) })
}