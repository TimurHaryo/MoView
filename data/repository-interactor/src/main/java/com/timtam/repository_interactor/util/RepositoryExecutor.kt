package com.timtam.repository_interactor.util

import com.timtam.common_kotlin.extension.isNotNull
import com.timtam.dto.extension.onError
import com.timtam.dto.extension.onNoData
import com.timtam.dto.extension.onSuccess
import com.timtam.dto.wrapper.Either
import com.timtam.dto.wrapper.Failure
import com.timtam.dto.wrapper.type.ErrorRequestType
import com.timtam.repository.mapper.DomainMapper
import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.DomainRemoteResource
import com.timtam.wrapper.exception.DomainException
import com.timtam.wrapper.type.ErrorDomainType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import java.net.HttpURLConnection

object RepositoryExecutor {

    fun <DTO : Any, DomainModel : Any> requestFromDataSource(
        mapper: DomainMapper<DTO, DomainModel>,
        remoteRequest: suspend () -> Either<Failure, DTO?>,
        localRequest: Flow<DTO>? = null,
        saveToLocal: (suspend (result: DTO) -> Unit)? = null
    ): Flow<DomainLocalResource<DomainModel>> = merge(
        flow {
            val callResult = remoteRequest.invoke()
            saveToLocal?.let { saver ->
                callResult.onSuccess {
                    saver.invoke(it!!)
                }
            }

            when (val resource = mapToUiResource(callResult, mapper)) {
                is DomainRemoteResource.Error -> emit(
                    DomainLocalResource.Error(resource.error)
                )
                is DomainRemoteResource.NoData -> emit(
                    DomainLocalResource.Error(
                        DomainException(
                            errorType = ErrorDomainType.UNKNOWN,
                            message = null
                        )
                    )
                )
                is DomainRemoteResource.Success -> emit(
                    DomainLocalResource.SuccessFromRemote
                )
            }
        },
        localRequest?.map {
            DomainLocalResource.Success(mapper.mapToDomainModel(it))
        } ?: emptyFlow()
    )

    private fun <DTO, DomainModel> mapToUiResource(
        dataWrapper: Either<Failure, DTO?>,
        mapper: DomainMapper<DTO, DomainModel>
    ): DomainRemoteResource<DomainModel> {
        dataWrapper
            .onSuccess {
                return DomainRemoteResource.Success(mapper.mapToDomainModel(it!!))
            }.onNoData {
                return DomainRemoteResource.NoData
            }.onError {
                return DomainRemoteResource.Error(it.mapToDomainException())
            }

        /**
         * Default value is [DomainRemoteResource.NoData]
         * */
        return DomainRemoteResource.NoData
    }

    private fun Failure.mapToDomainException(): DomainException {
        return if (errorCode.isNotNull()) {
            when (errorCode) {
                HttpURLConnection.HTTP_INTERNAL_ERROR,
                HttpURLConnection.HTTP_BAD_GATEWAY,
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
                    DomainException(ErrorDomainType.SYSTEM, message)
                }
                HttpURLConnection.HTTP_UNAVAILABLE -> {
                    DomainException(ErrorDomainType.MAINTENANCE, message)
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    DomainException(ErrorDomainType.UNAUTHORIZED, message)
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    DomainException(ErrorDomainType.NOT_FOUND, message)
                }
                else -> {
                    DomainException(ErrorDomainType.UNKNOWN, message)
                }
            }
        } else {
            when (errorType) {
                ErrorRequestType.NO_CONNECTION -> {
                    DomainException(ErrorDomainType.NETWORK, message)
                }
                ErrorRequestType.TIMEOUT -> {
                    DomainException(ErrorDomainType.CLIENT_TIMEOUT, message)
                }
                else -> {
                    DomainException(ErrorDomainType.UNKNOWN, message)
                }
            }
        }
    }
}
