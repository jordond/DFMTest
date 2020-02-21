package com.worldturtlemedia.dfmtest.common.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T

    protected abstract val bindingInflater: BindingInflater<T>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
        observeViewModel()
    }

    protected open fun setupViews() {}

    protected open fun observeViewModel() {}
}