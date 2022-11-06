package com.timtam.common.delegation.api

import com.timtam.common.extension.hasEmptyBody
import com.timtam.common.extension.isTotallySuccess
import com.timtam.common.model.type.ErrorRequestType
import com.timtam.common.model.valueobject.Either
import com.timtam.common.model.valueobject.Failure
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiRequestImpl : ApiRequest {
    override suspend fun <T> request(apiCall: suspend () -> Response<T>): Either<Failure, T> {
        return try {
            val response = apiCall.invoke()

            when {
                response.isTotallySuccess() -> Either.Success(response.body()!!)
                response.hasEmptyBody() -> parseError(
                    Failure(response.code(), Throwable(response.message()))
                )
                response.code() in 300..599 -> parseError(
                    Failure(response.code(), Throwable(response.message()))
                )
                else -> parseError(
                    Failure(response.code(), Throwable(response.message()))
                )
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is UnknownHostException -> parseError(
                    Failure(ErrorRequestType.NO_CONNECTION.ordinal, throwable)
                )
                is ConnectException -> parseError(
                    Failure(ErrorRequestType.NO_CONNECTION.ordinal, throwable)
                )
                is SocketTimeoutException -> parseError(
                    Failure(ErrorRequestType.TIMEOUT.ordinal, throwable)
                )
                else -> parseError(
                    Failure(ErrorRequestType.UNKNOWN_ERROR.ordinal, throwable)
                )
            }
        }
    }

    private fun parseError(failure: Failure): Either.Error<Failure> = Either.Error(failure)
}
