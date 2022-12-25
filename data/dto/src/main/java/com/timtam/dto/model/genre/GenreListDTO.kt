package com.timtam.dto.model.genre

import com.google.gson.annotations.SerializedName
import com.timtam.dto.model.base.PeekableDTO

data class GenreListDTO(

    @SerializedName("genres")
    val genres: List<GenreDTO?>? = null
) : PeekableDTO {

    override fun isEmpty(): Boolean = genres.orEmpty().filterNotNull().isEmpty()
}
