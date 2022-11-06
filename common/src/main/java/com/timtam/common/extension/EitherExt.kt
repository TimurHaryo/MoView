package com.timtam.common.extension

import com.timtam.common.model.valueobject.Either

inline fun <L, R> Either<L, R>.onError(error: (L) -> Unit): Either<L, R> {
    if (this is Either.Error) {
        error(this.error)
    }
    return this
}

inline fun <L, R> Either<L, R>.onSuccess(success: (R) -> Unit): Either<L, R> {
    if (this is Either.Success) {
        success(this.result)
    }
    return this
}
