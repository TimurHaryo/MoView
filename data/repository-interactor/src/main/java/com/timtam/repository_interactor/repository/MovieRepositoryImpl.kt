package com.timtam.repository_interactor.repository

import com.timtam.dto.model.movie.MovieListDTO
import com.timtam.dto.type.MovieStatusType
import com.timtam.local.CachedMovieDataSource
import com.timtam.remote.MovieDataSource
import com.timtam.repository.MovieRepository
import com.timtam.repository_interactor.mapper.MovieDomainMapper
import com.timtam.repository_interactor.util.RepositoryExecutor.requestDataFromCache
import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteMovieDataSource: MovieDataSource,
    private val cachedMovieDataSource: CachedMovieDataSource,
    private val movieMapper: MovieDomainMapper
) : MovieRepository {

    override fun getNowPlaying(page: Int, limit: Int): Flow<DomainLocalResource<List<MovieModel>>> =
        requestDataFromCache(
            remoteRequest = { remoteMovieDataSource.getNowPlaying(page) },
            mapper = movieMapper,
            localRequest = cachedMovieDataSource.getMovieSnips(limit, MovieStatusType.NOW_PLAYING).map {
                MovieListDTO(movies = it)
            },
            saveToLocal = { cachedMovieDataSource.insertAll(it.movies.orEmpty().filterNotNull()) }
        )
}
