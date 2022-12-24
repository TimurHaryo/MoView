package com.timtam.local_interactor.di

import com.timtam.local_interactor.dao.MovieDAO
import com.timtam.local_interactor.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Singleton
    @Provides
    fun provideMovieDAO(database: AppDatabase): MovieDAO = database.movieDAO()
}
