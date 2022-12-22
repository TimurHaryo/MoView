package com.timtam.dto.wrapper

sealed class Either<out Failed, out Result> {
    data class Success<out R>(val result: R) : Either<Nothing, R>()
    data class Error<out L>(val error: L) : Either<L, Nothing>()
}
