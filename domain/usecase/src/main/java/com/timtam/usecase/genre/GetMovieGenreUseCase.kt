package com.timtam.usecase.genre

import com.timtam.feature_item.genre.GenreItem
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

class GetMovieGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatcherProvider
) {

    private var hasBeenFetched = false

    operator fun invoke(): Flow<GenreState> = merge(
        flow { emit(GenreState.Loading) },
        movieRepository.getGenres().mapNotNull { resource ->
            when (resource) {
                is DomainLocalResource.Error -> GenreState.Error(resource.error)
                is DomainLocalResource.Success -> {
                    if (resource.data.isEmpty()) return@mapNotNull null

                    hasBeenFetched = true
                    GenreState.Success(
                        resource.data
                            .map { it.mapToPresentationItem() }
                            .sortedBy { it.id }
                    )
                }
                is DomainLocalResource.SuccessUpdateData -> {
                    if (hasBeenFetched) return@mapNotNull null

                    GenreState.Success(
                        resource.data
                            .map { it.mapToPresentationItem() }
                            .sortedBy { it.id }
                    )
                }
            }
        }
    ).flowOn(dispatcher.io)

    private fun GenreModel.mapToPresentationItem() = GenreItem(id = id, type = type)
}
