package com.timtam.repository

import com.timtam.wrapper.DomainLocalResource
import com.timtam.wrapper.model.GenreModel
import com.timtam.wrapper.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getNowPlayingSnips(page: Int): Flow<DomainLocalResource<List<MovieModel>>>

    fun getTopRatedSnips(page: Int): Flow<DomainLocalResource<List<MovieModel>>>

    fun getGenres(): Flow<DomainLocalResource<List<GenreModel>>>
}
