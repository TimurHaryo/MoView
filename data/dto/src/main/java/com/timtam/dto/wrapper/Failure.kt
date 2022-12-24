package com.timtam.dto.wrapper

import com.timtam.dto.type.ErrorRequestType

data class Failure(
    val errorCode: Int?,
    val errorType: ErrorRequestType,
    val message: String?
)
