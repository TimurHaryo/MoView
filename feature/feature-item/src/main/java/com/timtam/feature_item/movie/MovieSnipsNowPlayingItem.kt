package com.timtam.feature_item.movie

data class MovieSnipsNowPlayingItem(
    val id: Int,
    val isAdult: Boolean,
    val backdropPath: String,
    val genres: List<String>,
    val releaseDate: String,
    val title: String,
    val vote: Double
)
