package com.timtam.dto.model.movie

import com.google.gson.annotations.SerializedName
import com.timtam.dto.model.base.PeekableDTO

data class MovieListDTO(

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val movies: List<MovieDTO?>? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
) : PeekableDTO {

    override fun isEmpty(): Boolean = movies.orEmpty().filterNotNull().isEmpty()
}
