package com.timtam.local_interactor.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.timtam.dto.type.MovieStatusType
import com.timtam.local_interactor.util.ConverterUtil

class MovieConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromIdList(ids: List<Int>?): String? {
        return if (ids.isNullOrEmpty()) "[]" else gson.toJson(ids)
    }

    @TypeConverter
    fun toIdList(stringIds: String?): List<Int>? {
        if (stringIds.isNullOrEmpty()) return ArrayList(0)
        val resolvedType = ConverterUtil.resolveType<ArrayList<Int>?>()
        return try {
            gson.fromJson(stringIds, resolvedType)
        } catch (_: Exception) {
            ArrayList(0)
        }
    }

    @TypeConverter
    fun fromMovieStatusEnum(type: MovieStatusType): String = type.name

    @TypeConverter
    fun toMovieStatusEnum(stringType: String): MovieStatusType = enumValueOf(stringType)
}
