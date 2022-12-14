package com.timtam.common_kotlin.extension

fun Number?.isZero() = this == 0

fun Number?.isNotZero() = !isZero()
