package com.timtam.common_kotlin.extension

fun Number?.isZero() = this == 0

fun Number?.isNotZero() = !isZero()

fun Double?.orZero() = this ?: 0.0

fun Int?.orZero() = this ?: 0
