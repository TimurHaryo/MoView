package com.timtam.repository_interactor.repository

import com.timtam.dto.model.genre.GenreListDTO
import com.timtam.dto.model.movie.MovieListDTO
import com.timtam.dto.type.movie.MovieStatusType
import com.timtam.dto.type.show.ShowType
import com.timtam.local.CachedGenreDataSource
import com.timtam.local.CachedMovieDataSource
import com.timtam.remote.MovieDataSource
import com.timtam.repository.MovieRepository
import com.timtam.repository_interactor.mapper.GenreDomainMapper
import com.timtam.repository_interactor.mapper.MovieDomainMapper
import com.timtam.repository_interactor.util.RepositoryExecutor.requestDataFromCache
import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.GenreModel
import com.timtam.wrapper.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteMovieDataSource: MovieDataSource,
    private val cachedMovieDataSource: CachedMovieDataSource,
    private val cachedGenreDataSource: CachedGenreDataSource,
    private val movieMapper: MovieDomainMapper,
    private val genreMapper: GenreDomainMapper
) : MovieRepository {

    override fun getNowPlayingSnips(page: Int): Flow<DomainLocalResource<List<MovieModel>>> =
        requestDataFromCache(
            remoteRequest = { remoteMovieDataSource.getNowPlaying(page) },
            mapper = movieMapper,
            localRequest = cachedMovieDataSource.getMovieSnips(MovieStatusType.NOW_PLAYING)
                .map {
                    MovieListDTO(movies = it)
                },
            saveToLocal = {
                cachedMovieDataSource.insertAll(
                    it.movies
                        .orEmpty()
                        .filterNotNull()
                        .onEach { movie ->
                            movie.type = MovieStatusType.NOW_PLAYING
                        }
                )
            }
        )

    override fun getTopRatedSnips(page: Int): Flow<DomainLocalResource<List<MovieModel>>> =
        requestDataFromCache(
            remoteRequest = { remoteMovieDataSource.getTopRated(page) },
            mapper = movieMapper,
            localRequest = cachedMovieDataSource.getMovieSnips(MovieStatusType.TOP_RATED)
                .map {
                    MovieListDTO(movies = it)
                },
            saveToLocal = {
                cachedMovieDataSource.insertAll(
                    it.movies
                        .orEmpty()
                        .filterNotNull()
                        .onEach { movie ->
                            movie.type = MovieStatusType.TOP_RATED
                        }
                )
            }
        )

    override fun getGenres(): Flow<DomainLocalResource<List<GenreModel>>> =
        requestDataFromCache(
            remoteRequest = { remoteMovieDataSource.getMovieGenres() },
            mapper = genreMapper,
            localRequest = cachedGenreDataSource.getGenreByShowType(ShowType.MOVIE)
                .map {
                    GenreListDTO(genres = it)
                },
            saveToLocal = {
                cachedGenreDataSource.insertAll(
                    it.genres
                        .orEmpty()
                        .filterNotNull()
                        .onEach { genre ->
                            genre.showType = ShowType.MOVIE
                        }
                )
            },
            includeUpdatedData = false
        )
}
