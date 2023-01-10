package com.timtam.local_interactor.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timtam.dto.model.movie.MovieDTO
import com.timtam.dto.type.movie.MovieStatusType

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie_list WHERE type IS :type AND fromPage IS :fromPage")
    suspend fun getSnips(type: MovieStatusType, fromPage: Int): List<MovieDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieDTO>)
}
