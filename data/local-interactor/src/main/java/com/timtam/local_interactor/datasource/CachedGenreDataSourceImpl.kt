package com.timtam.local_interactor.datasource

import com.timtam.dto.model.genre.GenreDTO
import com.timtam.dto.type.show.ShowType
import com.timtam.local.CachedGenreDataSource
import com.timtam.local_interactor.dao.GenreDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CachedGenreDataSourceImpl @Inject constructor(
    private val genreDAO: GenreDAO
) : CachedGenreDataSource {

    override fun getGenreByShowType(type: ShowType): Flow<List<GenreDTO>> =
        flow { emit(genreDAO.getGenreByShowType(type)) }

    override suspend fun insertAll(genres: List<GenreDTO>) = genreDAO.insertAll(genres)
}
