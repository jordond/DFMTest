package com.worldturtlemedia.dfmtest.common.ktx

fun even(value: Int): Boolean = value % 2 == 0

fun odd(value: Int): Boolean = !even(value)