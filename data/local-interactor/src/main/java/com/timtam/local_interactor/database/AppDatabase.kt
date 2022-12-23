package com.timtam.local_interactor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timtam.dto.model.movie.MovieDTO
import com.timtam.local_interactor.converter.MovieConverter
import com.timtam.local_interactor.dao.MovieDAO

@Database(
    entities = [MovieDTO::class],
    version = 1
)
@TypeConverters(
    MovieConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO

    companion object {
        const val DB_NAME: String = "moview_db"
    }
}
