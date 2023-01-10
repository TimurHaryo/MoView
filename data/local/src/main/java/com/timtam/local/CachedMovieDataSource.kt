package com.timtam.local

import com.timtam.dto.model.movie.MovieDTO
import com.timtam.dto.type.movie.MovieStatusType
import kotlinx.coroutines.flow.Flow

interface CachedMovieDataSource {

    fun getMovieSnips(type: MovieStatusType): Flow<List<MovieDTO>>

    suspend fun insertAll(movies: List<MovieDTO>)
}
