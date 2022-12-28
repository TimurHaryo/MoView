package com.timtam.usecase.movie.state

import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.wrapper.exception.DomainException

sealed class MovieSnipsTopRatedState {
    object Loading : MovieSnipsTopRatedState()

    object Empty : MovieSnipsTopRatedState()

    data class Success(val data: List<MovieSnipsTopRatedItem>) : MovieSnipsTopRatedState()

    data class Error(val error: DomainException) : MovieSnipsTopRatedState()
}
