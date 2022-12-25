package com.timtam.local

import com.timtam.dto.model.genre.GenreDTO
import com.timtam.dto.type.show.ShowType
import kotlinx.coroutines.flow.Flow

interface CachedGenreDataSource {

    fun getGenreByShowType(type: ShowType): Flow<List<GenreDTO>>

    suspend fun insertAll(genres: List<GenreDTO>)
}
