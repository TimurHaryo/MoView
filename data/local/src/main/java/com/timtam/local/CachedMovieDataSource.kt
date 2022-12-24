package com.timtam.local

import com.timtam.dto.model.movie.MovieDTO
import com.timtam.dto.type.MovieStatusType
import kotlinx.coroutines.flow.Flow

interface CachedMovieDataSource {

    fun getMovieSnips(
        limit: Int,
        type: MovieStatusType
    ): Flow<List<MovieDTO>>

    suspend fun insertAll(movies: List<MovieDTO>)
}
