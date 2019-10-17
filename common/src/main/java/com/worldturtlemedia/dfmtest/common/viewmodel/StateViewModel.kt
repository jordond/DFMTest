package com.worldturtlemedia.dfmtest.common.viewmodel

import androidx.lifecycle.*
import com.github.ajalt.timberkt.d
import com.worldturtlemedia.dfmtest.common.ktx.mediatorLiveDataOf
import com.worldturtlemedia.dfmtest.common.ktx.observe

abstract class StateViewModel<S: State>(initialState: S): ViewModel() {

    private val _state = mediatorLiveDataOf(initialState)
    val state: LiveData<S>
        get() = _state

    protected val currentState: S
        get() = _state.value!!


    protected fun <T> addStateSource(liveData: LiveData<T>, onChange: (state: S, data: T) -> S) {
        _state.addSource(liveData) { data ->
            updateState { onChange(currentState, data) }
        }
    }

    protected fun updateState(block: S.() -> S?) {
        block(currentState)
            ?.let { _state.value = it }
            ?: d { "Returned null, not updating state" }
    }
}

interface State

fun <S: State, Property> LiveData<S>.observeProperty(
    owner: LifecycleOwner,
    property: (S) -> Property,
    onChange: (value: Property) -> Unit
) {
    map(property).distinctUntilChanged().observe(owner, onChange)
}
