package com.worldturtlemedia.dfmtest.common.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 */
class LazyAutoClearedValue<T : Any>(
    fragment: Fragment,
    private val createValue: (Fragment) -> T,
    private val onDestroy: ((T) -> Unit)? = null
) : ReadOnlyProperty<Fragment, T> {

    private var _value: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    _value?.let {
                        onDestroy?.invoke(it)
                        _value = null
                    }
                }
            })
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value
            ?: if (thisRef.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                createValue.invoke(thisRef).apply { _value = this }
            } else {
                throw IllegalStateException(
                    "should never call lazy-auto-cleared-value get when it might not be available"
                )
            }
    }
}

fun <T : Any> Fragment.autoClear(
    createValue: (Fragment) -> T,
    onDestroy: ((T) -> Unit)? = null
) = LazyAutoClearedValue(
    this,
    createValue,
    onDestroy
)