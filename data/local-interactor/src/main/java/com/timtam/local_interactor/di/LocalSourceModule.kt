package com.timtam.local_interactor.di

import com.timtam.local.CachedMovieDataSource
import com.timtam.local_interactor.datasource.CachedMovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface LocalSourceModule {

    @Binds
    fun bindCachedMovieDataSource(dataSource: CachedMovieDataSourceImpl): CachedMovieDataSource
}
