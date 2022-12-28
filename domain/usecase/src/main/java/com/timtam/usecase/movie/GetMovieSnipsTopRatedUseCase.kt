package com.timtam.usecase.movie

import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.repository.MovieRepository
import com.timtam.usecase.dispatcher.DispatcherProvider
import com.timtam.usecase.movie.state.MovieSnipsTopRatedState
import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetMovieSnipsTopRatedUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke(limit: Int): Flow<MovieSnipsTopRatedState> =
        merge(
            flow { emit(MovieSnipsTopRatedState.Loading) },
            movieRepository.getTopRated(1, limit).map { resource ->
                when (resource) {
                    is DomainLocalResource.Error -> MovieSnipsTopRatedState.Error(resource.error)
                    is DomainLocalResource.Success -> MovieSnipsTopRatedState.Success(
                        customizedItem(resource.data, limit)
                    )
                    is DomainLocalResource.SuccessUpdateData -> {
                        if (resource.data.isEmpty()) return@map MovieSnipsTopRatedState.Empty

                        MovieSnipsTopRatedState.Success(
                            customizedItem(resource.data, limit)
                        )
                    }
                }
            }
        ).flowOn(dispatcher.io)

    private fun MovieModel.mapToPresentationItem() =
        MovieSnipsTopRatedItem(
            id = id,
            title = title,
            posterPath = posterPath,
            rating = voteAverage
        )

    private fun customizedItem(data: List<MovieModel>, limit: Int) =
        data.map { it.mapToPresentationItem() }
            .sortedByDescending { it.rating }
            .take(limit)
}
