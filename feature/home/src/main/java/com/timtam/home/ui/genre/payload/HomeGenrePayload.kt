package com.timtam.home.ui.genre.payload

import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.home.ui.home.adapter.payload.HomePayload

sealed class HomeGenrePayload : HomePayload {
    data class ShowData(val genres: List<GenreHomeItem>) : HomeGenrePayload()

    object ShowError : HomeGenrePayload()
}
