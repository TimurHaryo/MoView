package com.timtam.local_interactor.datasource

import com.timtam.dto.model.movie.MovieDTO
import com.timtam.dto.type.movie.MovieStatusType
import com.timtam.local.CachedMovieDataSource
import com.timtam.local_interactor.dao.MovieDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CachedMovieDataSourceImpl @Inject constructor(
    private val movieDAO: MovieDAO
) : CachedMovieDataSource {

    override fun getMovieSnips(
        type: MovieStatusType,
        fromPage: Int
    ): Flow<List<MovieDTO>> = flow { emit(movieDAO.getSnips(type, fromPage)) }

    override suspend fun insertAll(movies: List<MovieDTO>) = movieDAO.insertAll(movies)
}
