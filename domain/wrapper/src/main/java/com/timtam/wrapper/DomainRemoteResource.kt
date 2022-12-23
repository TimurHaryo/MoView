package com.timtam.wrapper

import com.timtam.wrapper.exception.DomainException

sealed class DomainRemoteResource<out Data> {
    data class Success<Result>(val data: Result) : DomainRemoteResource<Result>()

    object NoData : DomainRemoteResource<Nothing>()

    data class Error(val error: DomainException) : DomainRemoteResource<Nothing>()
}
