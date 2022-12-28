package com.timtam.home.ui.nowplaying.payload

import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.home.ui.home.adapter.payload.HomePayload

sealed class HomeMovieNowPlayingPayload : HomePayload {
    data class ShowData(val movies: List<MovieSnipsNowPlayingItem>) : HomeMovieNowPlayingPayload()

    object ShowEmpty : HomeMovieNowPlayingPayload()

    object ShowError : HomeMovieNowPlayingPayload()
}
