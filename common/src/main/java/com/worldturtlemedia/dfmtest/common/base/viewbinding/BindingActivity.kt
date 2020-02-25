package com.worldturtlemedia.dfmtest.common.base.viewbinding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<T : ViewBinding> : AppCompatActivity() {

    protected abstract val binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupViews()
        observeViewModel()
    }

    protected open fun setupViews() {}

    protected open fun observeViewModel() {}
}