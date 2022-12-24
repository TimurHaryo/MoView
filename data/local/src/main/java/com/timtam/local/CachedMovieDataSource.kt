package com.timtam.local

import com.timtam.dto.model.movie.MovieDTO
import kotlinx.coroutines.flow.Flow

interface CachedMovieDataSource {

    suspend fun getMovieSnips(limit: Int): Flow<List<MovieDTO>>

    suspend fun insertAll(movies: List<MovieDTO>)
}
