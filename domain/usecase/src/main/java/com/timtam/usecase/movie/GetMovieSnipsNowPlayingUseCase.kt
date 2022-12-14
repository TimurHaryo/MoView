package com.timtam.usecase.movie

import com.timtam.common_kotlin.extension.reformatDate
import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.repository.MovieRepository
import com.timtam.usecase.dispatcher.DispatcherProvider
import com.timtam.usecase.movie.state.MovieSnipsNowPlayingState
import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetMovieSnipsNowPlayingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: DispatcherProvider
) {

    operator fun invoke(genres: List<GenreHomeItem>, limit: Int): Flow<MovieSnipsNowPlayingState> {
        var isTotallyEmpty = false
        return merge(
            flow { emit(MovieSnipsNowPlayingState.Loading) },
            movieRepository.getNowPlayingSnips(1).map { resource ->
                when (resource) {
                    is DomainLocalResource.Error -> MovieSnipsNowPlayingState.Error(resource.error)
                    is DomainLocalResource.Success -> {
                        isTotallyEmpty = resource.data.isEmpty()
                        MovieSnipsNowPlayingState.Success(
                            customizedItem(
                                resource.data,
                                genres,
                                limit
                            )
                        )
                    }
                    is DomainLocalResource.SuccessUpdateData -> {
                        if (resource.data.isEmpty() && isTotallyEmpty) return@map MovieSnipsNowPlayingState.Empty

                        MovieSnipsNowPlayingState.Success(
                            customizedItem(
                                resource.data,
                                genres,
                                limit
                            )
                        )
                    }
                }
            }
        ).flowOn(dispatcher.io)
    }

    private fun MovieModel.mapToPresentationItem(genres: List<GenreHomeItem>) =
        MovieSnipsNowPlayingItem(
            id = id,
            isAdult = isAdult,
            backdropPath = backdropPath,
            genreGroup = genreIds.findCorrespondingGenre(genres).joinToString(GENRE_SEPARATOR),
            releaseDate = reformatDate(releaseDate),
            title = title,
            rating = voteAverage
        )

    private fun List<Int>.findCorrespondingGenre(genres: List<GenreHomeItem>): List<String> =
        mapNotNull { id ->
            genres.firstOrNull { it.id == id }?.type
        }

    private fun customizedItem(data: List<MovieModel>, genres: List<GenreHomeItem>, limit: Int) =
        data.map { it.mapToPresentationItem(genres) }
            .sortedByDescending { it.rating }
            .take(limit)

    companion object {
        private const val GENRE_SEPARATOR = " | "
    }
}
