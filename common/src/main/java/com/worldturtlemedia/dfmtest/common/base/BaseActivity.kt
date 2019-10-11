package com.worldturtlemedia.dfmtest.common.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected abstract fun layout(): Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout())

        afterCreate()
        setupViews()
    }

    open fun afterCreate() {}

    open fun setupViews() {}
}