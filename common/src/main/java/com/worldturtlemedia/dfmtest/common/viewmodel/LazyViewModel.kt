package com.worldturtlemedia.dfmtest.common.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

// TODO:  Had to include this because the `AudioListFragment` for some reason couldn't see the built in one

/**
 * Delegate to lazily create a [ViewModel].
 *
 * @param[vmClass] Type of [ViewModel] to create.
 * @param[storeOwnerProducer] Lambda to produce a [ViewModelStoreOwner] scope for the [ViewModel].
 * @param[factoryProducer] Lambda to produce the [ViewModelProvider.Factory] that will create the [ViewModel].
 */
class LazyViewModel<VM : ViewModel>(
    private val vmClass: KClass<VM>,
    private val storeOwnerProducer: () -> ViewModelStoreOwner,
    private val factoryProducer: () -> ViewModelProvider.Factory
) : Lazy<VM> {

    private var cached: VM? = null

    override val value: VM
        get() = cached
            ?: ViewModelProvider(storeOwnerProducer(), factoryProducer())
                .get(vmClass.java)
                .also { cached = it }

    override fun isInitialized(): Boolean = cached != null

    companion object {

        /**
         * Default producer for creating a [ViewModel].  It will use the default no-arg constructor
         * of [VM].  If [VM] does not have a no-arg constructor, an error will be thrown.
         */
        val defaultFactoryProducer = { ViewModelProvider.NewInstanceFactory() }
    }
}

/**
 * Delegate to lazily create a [ViewModel] inside of a [FragmentActivity].
 *
 * @param[VM] Type of [ViewModel] to create.
 * @param[storeOwnerProducer] Lambda to produce a [ViewModelStoreOwner] scope for the [ViewModel]. Defaults to [FragmentActivity] scope.
 * @param[factoryProducer] Lambda to produce a [ViewModelProvider.Factory], defaults to empty constructor for [VM].
 * @return Lazy delegate for creating [FragmentActivity] scoped [ViewModel].
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModels(
    noinline storeOwnerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: () -> ViewModelProvider.Factory = LazyViewModel.defaultFactoryProducer
) = LazyViewModel(VM::class, storeOwnerProducer, factoryProducer)

/**
 * Delegate to lazily create a [ViewModel] inside of a [Fragment].
 *
 * @param[VM] Type of [ViewModel] to create.
 * @param[storeOwnerProducer] Lambda to produce a [ViewModelStoreOwner] scope for the [ViewModel]. Defaults to [Fragment] scope.
 * @param[factoryProducer] Lambda to produce a [ViewModelProvider.Factory], defaults to empty constructor for [VM].
 * @return Lazy delegate for creating [Fragment] scoped [ViewModel].
 */
inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline storeOwnerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: () -> ViewModelProvider.Factory = LazyViewModel.defaultFactoryProducer
) = LazyViewModel(VM::class, storeOwnerProducer, factoryProducer)

/**
 * Delegate to lazily create a shared [ViewModel] that is scoped to a [Fragment]'s parent [FragmentActivity].
 *
 * @param[VM] Type of [ViewModel] to create.
 * @param[storeOwnerProducer] Lambda to produce a [ViewModelStoreOwner] scope for the [ViewModel]. Defaults to parent [FragmentActivity] scope.
 * @param[factoryProducer] Lambda to produce a [ViewModelProvider.Factory], defaults to empty constructor for [VM].
 * @return Lazy delegate for creating a shared [FragmentActivity] scoped [ViewModel].
 */
inline fun <reified VM : ViewModel> Fragment.sharedViewModels(
    noinline storeOwnerProducer: () -> ViewModelStoreOwner = ::requireActivity,
    noinline factoryProducer: () -> ViewModelProvider.Factory = LazyViewModel.defaultFactoryProducer
) = LazyViewModel(VM::class, storeOwnerProducer, factoryProducer)
