package com.timtam.home.ui.nowplaying.argument

import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.home.ui.base.StatefulArg

data class HomeMovieNowPlayingArg(
    var nowPlayingItems: List<MovieSnipsNowPlayingItem> = emptyList()
) : StatefulArg()
