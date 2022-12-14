package com.timtam.common_kotlin.extension

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun Any?.isNull() = this == null

@OptIn(ExperimentalContracts::class)
fun Any?.isNotNull(): Boolean {
    contract {
        returns(true) implies (this@isNotNull != null)
    }
    return this != null
}
