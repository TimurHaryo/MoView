package com.timtam.home.ui.toprated.argument

import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.home.ui.base.StatefulArg

data class HomeMovieTopRatedArg(
    var topRatedItems: List<MovieSnipsTopRatedItem> = emptyList()
) : StatefulArg()
