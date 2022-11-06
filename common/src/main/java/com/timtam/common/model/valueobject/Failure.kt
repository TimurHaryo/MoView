package com.timtam.common.model.valueobject

import com.timtam.common.model.type.ErrorRequestType

data class Failure(
    val errorType: ErrorRequestType,
    val throwable: Throwable
)
