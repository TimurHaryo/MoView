package com.timtam.remote_interactor.di

import com.timtam.remote.MovieDataSource
import com.timtam.remote_interactor.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RemoteSourceModule {

    @Binds
    fun bindMovieDataSource(dataSource: MovieDataSourceImpl): MovieDataSource
}
