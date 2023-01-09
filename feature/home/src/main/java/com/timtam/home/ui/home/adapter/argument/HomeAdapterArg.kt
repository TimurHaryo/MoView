package com.timtam.home.ui.home.adapter.argument

import com.timtam.home.ui.genre.argument.HomeGenreArg
import com.timtam.home.ui.nowplaying.argument.HomeMovieNowPlayingArg
import com.timtam.home.ui.toprated.argument.HomeMovieTopRatedArg

data class HomeAdapterArg(
    var genreArg: HomeGenreArg = HomeGenreArg(),
    var nowPlayingArg: HomeMovieNowPlayingArg = HomeMovieNowPlayingArg(),
    var topRatedArg: HomeMovieTopRatedArg = HomeMovieTopRatedArg()
)
