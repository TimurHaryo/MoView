package com.timtam.usecase.genre

import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.repository.MovieRepository
import com.timtam.usecase.dispatcher.DispatcherProvider
import com.timtam.usecase.genre.state.GenreState
import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.GenreModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetMovieSnipsGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke(): Flow<GenreState> = merge(
        flow { emit(GenreState.Loading) },
        movieRepository.getGenres().mapNotNull { resource ->
            when (resource) {
                is DomainLocalResource.Error -> GenreState.Error(resource.error)
                is DomainLocalResource.Success -> {
                    if (resource.data.isEmpty()) return@mapNotNull null

                    GenreState.Success(
                        resource.data
                            .map { it.mapToPresentationItem() }
                            .sortedBy { it.id }
                    )
                }
                is DomainLocalResource.SuccessUpdateData -> {
                    GenreState.Success(
                        resource.data
                            .map { it.mapToPresentationItem() }
                            .sortedBy { it.id }
                    )
                }
            }
        }
    ).flowOn(dispatcher.io)

    private fun GenreModel.mapToPresentationItem() = GenreHomeItem(
        id = id,
        type = type,
        imageTag = type.split(" ").getOrNull(0).orEmpty()
    )
}
