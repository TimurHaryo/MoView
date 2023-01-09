package com.timtam.home.ui.genre.argument

import com.timtam.feature_item.genre.GenreHomeItem

data class HomeGenreArg(
    var genreItems: List<GenreHomeItem> = emptyList(),
    var isError: Boolean = false
)
