package com.timtam.home.ui.nowplaying.argument

import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem

data class HomeMovieNowPlayingArg(
    var nowPlayingItems: List<MovieSnipsNowPlayingItem> = emptyList(),
    var isEmpty: Boolean = false,
    var isError: Boolean = false,
    var isLoading: Boolean = false
)
