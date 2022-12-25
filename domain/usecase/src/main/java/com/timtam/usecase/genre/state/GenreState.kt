package com.timtam.usecase.genre.state

import com.timtam.feature_item.genre.GenreItem
import com.timtam.wrapper.exception.DomainException

sealed class GenreState {
    object Loading : GenreState()

    data class Success(val data: List<GenreItem>) : GenreState()

    data class Error(val error: DomainException) : GenreState()
}
