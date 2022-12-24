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

    operator fun invoke(page: Int, limit: Int): Flow<MovieSnipsNowPlayingState> = merge(
        flow { emit(MovieSnipsNowPlayingState.Loading) },
        movieRepository.getNowPlaying(page, limit).mapNotNull { resource ->
            when (resource) {
                is DomainLocalResource.Error -> MovieSnipsNowPlayingState.Error(resource.error)
                is DomainLocalResource.Success -> MovieSnipsNowPlayingState.Success(
                    resource.data.map { it.mapToPresentationItem() }
                )
                is DomainLocalResource.SuccessFromRemote -> null
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
}
