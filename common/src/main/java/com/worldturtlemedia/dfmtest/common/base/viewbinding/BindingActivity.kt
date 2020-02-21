package com.worldturtlemedia.dfmtest.common.base.viewbinding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<T : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    protected abstract val bindingInflater: BindingInflater<T>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        setupViews()
        observeViewModel()
    }

    protected open fun setupViews() {}

    protected open fun observeViewModel() {}
}