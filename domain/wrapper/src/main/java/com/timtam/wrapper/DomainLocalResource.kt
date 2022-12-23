package com.timtam.wrapper

import com.timtam.wrapper.exception.DomainException

sealed class DomainLocalResource<out Data> {
    data class Success<Result>(val data: Result) : DomainLocalResource<Result>()

    object SuccessFromRemote : DomainLocalResource<Nothing>()

    data class Error(val error: DomainException) : DomainLocalResource<Nothing>()
}
