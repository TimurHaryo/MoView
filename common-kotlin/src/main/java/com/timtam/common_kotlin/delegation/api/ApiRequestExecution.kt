package com.timtam.common_kotlin.delegation.api

import com.timtam.common_kotlin.model.valueobject.Either
import com.timtam.common_kotlin.model.valueobject.Failure
import retrofit2.Response

interface ApiRequestExecution {
    suspend fun <T> request(apiCall: suspend () -> Response<T>): Either<Failure, T>
}
