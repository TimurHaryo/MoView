package com.timtam.wrapper.model

data class MovieModel(
    val id: Int,
    val isAdult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val isVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
