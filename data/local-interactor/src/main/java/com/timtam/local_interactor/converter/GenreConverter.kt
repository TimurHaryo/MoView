package com.timtam.local_interactor.converter

import androidx.room.TypeConverter
import com.timtam.dto.type.show.ShowType

class GenreConverter {

    @TypeConverter
    fun fromShowType(type: ShowType): String = type.name

    @TypeConverter
    fun toShowType(stringType: String): ShowType = enumValueOf(stringType)
}
