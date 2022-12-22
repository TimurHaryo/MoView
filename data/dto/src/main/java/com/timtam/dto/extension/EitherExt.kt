package com.timtam.dto.extension

import com.timtam.common_kotlin.extension.isNotNull
import com.timtam.common_kotlin.extension.isNull
import com.timtam.dto.wrapper.Either

inline fun <L, R> Either<L, R>.onError(error: (L) -> Unit): Either<L, R> {
    if (this is Either.Error) {
        error(this.error)
    }
    return this
}

inline fun <L, R> Either<L, R>.onSuccess(success: (R) -> Unit): Either<L, R> {
    if (this is Either.Success && this.result.isNotNull()) {
        success(this.result)
    }
    return this
}

inline fun <L, R> Either<L, R>.onEmpty(success: (R) -> Unit): Either<L, R> {
    if (this is Either.Success && this.result.isNull()) {
        success(this.result)
    }
    return this
}
