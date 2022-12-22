package com.timtam.dto.model.movie

import com.google.gson.annotations.SerializedName

data class MovieListDTO(

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val movies: List<MovieDTO?>? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)
