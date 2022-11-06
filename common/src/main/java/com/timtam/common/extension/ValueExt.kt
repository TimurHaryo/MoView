package com.timtam.common.extension

fun Any?.isNull() = this == null

fun Any?.isNotNull() = !this.isNull()
