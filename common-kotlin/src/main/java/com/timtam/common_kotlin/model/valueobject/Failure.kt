package com.timtam.common_kotlin.model.valueobject

data class Failure(
    val errorCode: Int,
    val throwable: Throwable
)
