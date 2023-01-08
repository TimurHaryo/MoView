package com.timtam.home.ui.home.adapter.argument

import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.feature_item.movie.MovieSnipsTopRatedItem

data class HomeAdapterArg(
    var genreArgs: List<GenreHomeItem> = emptyList(),
    var nowPlayingArgs: List<MovieSnipsNowPlayingItem> = emptyList(),
    var topRatedArgs: List<MovieSnipsTopRatedItem> = emptyList()
)
