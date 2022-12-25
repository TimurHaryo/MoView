package com.timtam.remote_interactor.api

import com.timtam.dto.model.genre.GenreListDTO
import com.timtam.dto.model.movie.MovieListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int): Response<MovieListDTO>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<GenreListDTO>
}
