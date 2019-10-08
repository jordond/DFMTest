package com.worldturtlemedia.dfmtest.common.base

import androidx.annotation.LayoutRes

interface HasLayout {

    @LayoutRes
    fun layout(): Int
}