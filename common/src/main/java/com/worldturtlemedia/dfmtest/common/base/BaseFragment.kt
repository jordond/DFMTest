package com.worldturtlemedia.dfmtest.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun layout(): Int

    protected val owner: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layout(), container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
        subscribeViewModel()
    }

    protected open fun setupViews() {}

    protected open fun subscribeViewModel() {}
}