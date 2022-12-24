package com.timtam.remote_interactor.util

import com.timtam.dto.type.ErrorRequestType
import com.timtam.dto.wrapper.Either
import com.timtam.dto.wrapper.Failure
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object DataSourceExecutor {
    suspend fun <T> request(apiCall: suspend () -> Response<T>): Either<Failure, T?> {
        return try {
            val response = apiCall.invoke()

            when {
                response.isSuccessful -> Either.Success(response.body())
                response.code() in 300..499 -> parseError(
                    Failure(response.code(), ErrorRequestType.CLIENT_ERROR, response.message())
                )
                response.code() in 500..599 -> parseError(
                    Failure(response.code(), ErrorRequestType.SERVER_ERROR, response.message())
                )
                else -> parseError(
                    Failure(response.code(), ErrorRequestType.UNKNOWN_ERROR, response.message())
                )
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is UnknownHostException, is ConnectException -> parseError(
                    Failure(null, ErrorRequestType.NO_CONNECTION, throwable.localizedMessage)
                )
                is SocketTimeoutException -> parseError(
                    Failure(null, ErrorRequestType.TIMEOUT, throwable.localizedMessage)
                )
                else -> parseError(
                    Failure(null, ErrorRequestType.UNKNOWN_ERROR, throwable.localizedMessage)
                )
            }
        }
    }

    private fun parseError(failure: Failure): Either.Error<Failure> = Either.Error(failure)
}
