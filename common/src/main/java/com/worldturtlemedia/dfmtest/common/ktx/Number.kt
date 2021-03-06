package com.worldturtlemedia.dfmtest.common.ktx

import android.content.res.Resources

fun even(value: Int): Boolean = value % 2 == 0

fun odd(value: Int): Boolean = !even(value)

val Int.dp: Int
    get() = toFloat().dp.toInt()

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)