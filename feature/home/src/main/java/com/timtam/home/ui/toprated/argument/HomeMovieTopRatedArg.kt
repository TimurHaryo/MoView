package com.timtam.home.ui.toprated.argument

import com.timtam.feature_item.movie.MovieSnipsTopRatedItem

data class HomeMovieTopRatedArg(
    var topRatedItems: List<MovieSnipsTopRatedItem> = emptyList(),
    var isEmpty: Boolean = false,
    var isError: Boolean = false,
    var isLoading: Boolean = false
)
