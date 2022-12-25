package com.timtam.local_interactor.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timtam.dto.model.genre.GenreDTO
import com.timtam.dto.type.show.ShowType

@Dao
interface GenreDAO {

    @Query("SELECT * FROM genre WHERE showType IS :type")
    suspend fun getGenreByShowType(type: ShowType): List<GenreDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreDTO>)
}
