package com.timtam.dto.wrapper

data class Failure(
    val errorCode: Int,
    val throwable: Throwable
)
