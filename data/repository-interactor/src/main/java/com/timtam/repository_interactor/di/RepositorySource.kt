package com.timtam.repository_interactor.di

import com.timtam.repository.MovieRepository
import com.timtam.repository_interactor.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@dagger.Module
interface RepositorySource {

    @Binds
    fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository
}
