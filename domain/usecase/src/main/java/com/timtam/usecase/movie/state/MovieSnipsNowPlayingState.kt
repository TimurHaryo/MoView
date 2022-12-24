package com.timtam.usecase.movie.state

import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.wrapper.exception.DomainException

sealed class MovieSnipsNowPlayingState {
    object Loading : MovieSnipsNowPlayingState()

    object Empty : MovieSnipsNowPlayingState()

    data class Success(val data: List<MovieSnipsNowPlayingItem>) : MovieSnipsNowPlayingState()

    data class Error(val error: DomainException) : MovieSnipsNowPlayingState()
}
