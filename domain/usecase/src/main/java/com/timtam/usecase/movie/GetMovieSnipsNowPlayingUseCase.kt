package com.timtam.usecase.movie

import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.repository.MovieRepository
import com.timtam.usecase.dispatcher.DispatcherProvider
import com.timtam.usecase.movie.state.MovieSnipsNowPlayingState
import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetMovieSnipsNowPlayingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke(limit: Int): Flow<MovieSnipsNowPlayingState> = merge(
        flow { emit(MovieSnipsNowPlayingState.Loading) },
        movieRepository.getNowPlaying(1, limit).mapNotNull { resource ->
            when (resource) {
                is DomainLocalResource.Error -> MovieSnipsNowPlayingState.Error(resource.error)
                is DomainLocalResource.Success -> {
                    if (resource.data.isEmpty()) return@mapNotNull null

                    MovieSnipsNowPlayingState.Success(customizedItem(resource.data, limit))
                }
                is DomainLocalResource.SuccessUpdateData -> {
                    if (resource.data.isEmpty()) return@mapNotNull MovieSnipsNowPlayingState.Empty

                    MovieSnipsNowPlayingState.Success(customizedItem(resource.data, limit))
                }
            }
        }
    ).flowOn(dispatcher.io)

    private fun MovieModel.mapToPresentationItem() =
        MovieSnipsNowPlayingItem(
            id = id,
            isAdult = isAdult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            releaseDate = releaseDate,
            title = title,
            vote = voteAverage
        )

    private fun customizedItem(data: List<MovieModel>, limit: Int) =
        data.map { it.mapToPresentationItem() }
            .sortedBy { it.id }
            .take(limit)
}
