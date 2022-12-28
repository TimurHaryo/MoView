package com.timtam.repository

import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.GenreModel
import com.timtam.wrapper.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getNowPlaying(page: Int, limit: Int): Flow<DomainLocalResource<List<MovieModel>>>

    fun getTopRated(page: Int, limit: Int): Flow<DomainLocalResource<List<MovieModel>>>

    fun getGenres(): Flow<DomainLocalResource<List<GenreModel>>>
}
