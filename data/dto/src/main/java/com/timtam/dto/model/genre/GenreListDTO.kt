package com.timtam.dto.model.genre

import com.google.gson.annotations.SerializedName

data class GenreListDTO(

    @SerializedName("genres")
    val genres: List<GenreDTO?>? = null
)
