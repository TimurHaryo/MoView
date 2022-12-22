package com.timtam.remote_interactor

import com.timtam.dto.wrapper.Either
import com.timtam.dto.wrapper.Failure
import com.timtam.dto.wrapper.type.ErrorRequestType
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
                    Failure(ErrorRequestType.CLIENT_ERROR.errorCode, Throwable(response.message()))
                )
                response.code() in 500..599 -> parseError(
                    Failure(ErrorRequestType.SERVER_ERROR.errorCode, Throwable(response.message()))
                )
                else -> parseError(
                    Failure(response.code(), Throwable(response.message()))
                )
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is UnknownHostException, is ConnectException -> parseError(
                    Failure(ErrorRequestType.NO_CONNECTION.errorCode, throwable)
                )
                is SocketTimeoutException -> parseError(
                    Failure(ErrorRequestType.TIMEOUT.errorCode, throwable)
                )
                else -> parseError(
                    Failure(ErrorRequestType.UNKNOWN_ERROR.errorCode, throwable)
                )
            }
        }
    }

    private fun parseError(failure: Failure): Either.Error<Failure> = Either.Error(failure)
}
