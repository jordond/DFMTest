package com.worldturtlemedia.dfmtest.common.base.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.worldturtlemedia.dfmtest.common.ktx.mediatorLiveDataOf
import com.worldturtlemedia.dfmtest.common.ktx.simpleName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach

interface State

abstract class StateViewModel<S : State>(initialState: S) : ViewModel() {

    companion object {

        val TAG = StateViewModel.simpleName
    }

    /**
     * Create a Single source of truth for the state by hiding the mutable LiveData.
     */
    private val _state = mediatorLiveDataOf(initialState)
    val state: LiveData<S>
        get() = _state

    /**
     * Convenient accessor to get the current value of the state.  We know the state cannot be
     * null, because we pass an initial state in the constructor.
     */
    protected val currentState: S
        get() = _state.value!!

    /**
     * Create an [actor] that will consume each [Update], invoke the lambda, then update the state
     * if needed.
     *
     * An [actor] is used to prevent race-conditions, and stale [State] being sent to the [Update].
     */
    @UseExperimental(ObsoleteCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    private val updateStateActor = viewModelScope.actor<Update<S>> {
        channel.consumeEach { update ->
            update.invoke(currentState)
                ?.let { _state.value = it }
                ?: Log.d(TAG, "Returned null, not updating state")
        }
    }

    /**
     * Adapt a [LiveData] into state updates.
     *
     * This is useful when consuming a flow or [LiveData] from another source, like a Repository.
     *
     * @see MediatorLiveData.addSource
     * @param[source] LiveData to adapt.
     * @param[onChange] Lambda expression to convert the [source] data to a [S].
     */
    protected fun <T> addStateSource(source: LiveData<T>, onChange: S.(data: T) -> S?) {
        _state.addSource(source) { data ->
            updateState { onChange(currentState, data) }
        }
    }

    /**
     * Wrapper for [MediatorLiveData.removeSource].
     *
     * @param[source] Source to remove.
     */
    protected fun <V> removeStateSource(source: LiveData<V>): Unit = _state.removeSource(source)

    /**
     * Offer an update to the [updateStateActor].
     *
     * Instead of updating the [_state] right away, we instead use an [actor] that will consume
     * the "update" events sequentially, so we can avoid race-conflicts.  And the [block] will always
     * be called with the latest state.
     *
     * @param[block] A lambda scoped to the current [State].
     */
    @UseExperimental(ExperimentalCoroutinesApi::class)
    protected fun updateState(block: suspend S.() -> S?) {
        if (updateStateActor.isClosedForSend) {
            i { "State actor is closed, cannot offer update." }
        } else {
            updateStateActor.offer(block)
        }
    }

    protected fun noUpdate() = null
}

private typealias Update<S> = suspend S.() -> S?
