package com.timtam.dto.model.genre

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_genre")
data class GenreDTO(

    @PrimaryKey
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null
)
