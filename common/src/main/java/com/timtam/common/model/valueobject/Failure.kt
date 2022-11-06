package com.timtam.common.model.valueobject

data class Failure(
    val errorCode: Int,
    val throwable: Throwable
)
