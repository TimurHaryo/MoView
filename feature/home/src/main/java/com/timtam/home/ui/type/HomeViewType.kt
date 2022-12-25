package com.timtam.home.ui.type

enum class HomeViewType {
    HEADER,
    GENRE,
    NOW_PLAYING,
    TOP_RATED;

    companion object {
        val defaultOrder = values().toList()
    }
}
