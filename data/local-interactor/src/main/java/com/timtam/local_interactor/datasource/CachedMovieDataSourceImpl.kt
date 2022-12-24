package com.timtam.local_interactor.datasource

import com.timtam.dto.model.movie.MovieDTO
import com.timtam.local.CachedMovieDataSource
import com.timtam.local_interactor.dao.MovieDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CachedMovieDataSourceImpl @Inject constructor(
    private val movieDAO: MovieDAO
) : CachedMovieDataSource {

    override suspend fun getMovieSnips(limit: Int): Flow<List<MovieDTO>> = flow {
        emit(movieDAO.getSnips(limit))
    }

    override suspend fun insertAll(movies: List<MovieDTO>) = movieDAO.insertAll(movies)
}
