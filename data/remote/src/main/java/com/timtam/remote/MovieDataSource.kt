package com.timtam.remote

import com.timtam.dto.model.movie.MovieListDTO
import com.timtam.dto.wrapper.Either
import com.timtam.dto.wrapper.Failure

interface MovieDataSource {

    suspend fun getNowPlaying(page: Int): Either<Failure, MovieListDTO?>
}
