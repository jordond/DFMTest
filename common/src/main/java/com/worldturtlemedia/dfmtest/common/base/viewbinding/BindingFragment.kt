package com.worldturtlemedia.dfmtest.common.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.worldturtlemedia.dfmtest.common.base.BaseFragment

abstract class BindingFragment<T : ViewBinding> : BaseFragment() {

    override fun layout(): Int {
        throw IllegalStateException("Should never be called!")
    }

    protected lateinit var binding: T

    protected abstract val bindingInflater: BindingInflater<T>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater(inflater)
        return binding.root
    }
}