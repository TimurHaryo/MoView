package com.timtam.common.delegation.api

import com.timtam.common.model.valueobject.Either
import com.timtam.common.model.valueobject.Failure
import retrofit2.Response

interface ApiRequest {
    suspend fun <T> request(apiCall: suspend () -> Response<T>): Either<Failure, T>
}
