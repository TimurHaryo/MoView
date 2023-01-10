package com.timtam.home.ui.toprated.payload

import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.home.ui.home.adapter.payload.HomePayload

sealed class HomeMovieTopRatedPayload : HomePayload {
    data class ShowData(val movies: List<MovieSnipsTopRatedItem>) : HomeMovieTopRatedPayload()

    data class ShowLoading(val isLoading: Boolean) : HomeMovieTopRatedPayload()

    object ShowEmpty : HomeMovieTopRatedPayload()

    object ShowError : HomeMovieTopRatedPayload()
}
