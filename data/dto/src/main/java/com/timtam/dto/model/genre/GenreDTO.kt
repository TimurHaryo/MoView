package com.timtam.dto.model.genre

import com.google.gson.annotations.SerializedName

data class GenreDTO(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null
)
