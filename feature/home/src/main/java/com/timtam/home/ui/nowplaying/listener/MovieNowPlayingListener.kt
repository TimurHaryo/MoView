package com.timtam.home.ui.nowplaying.listener

interface MovieNowPlayingListener {
    fun onMoreClick()
    fun onMovieClick(movieId: Int)
    fun onTryAgainClick()
}
