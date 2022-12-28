package com.timtam.remote_interactor.datasource

import com.timtam.dto.model.genre.GenreListDTO
import com.timtam.dto.model.movie.MovieListDTO
import com.timtam.dto.wrapper.Either
import com.timtam.dto.wrapper.Failure
import com.timtam.remote.MovieDataSource
import com.timtam.remote_interactor.api.MovieApi
import com.timtam.remote_interactor.util.DataSourceExecutor.request
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieDataSource {

    override suspend fun getNowPlaying(page: Int): Either<Failure, MovieListDTO?> = request {
        movieApi.getNowPlaying(page)
    }

    override suspend fun getTopRated(page: Int): Either<Failure, MovieListDTO?> = request {
        movieApi.getTopRated(page)
    }

    override suspend fun getMovieGenres(): Either<Failure, GenreListDTO?> = request {
        movieApi.getMovieGenres()
    }
}
