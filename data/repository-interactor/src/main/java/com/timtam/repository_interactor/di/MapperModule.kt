package com.timtam.repository_interactor.di

import com.timtam.repository_interactor.mapper.MovieDomainMapper
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@dagger.Module
class MapperModule {

    @Singleton
    @Provides
    fun provideMovieDomainMapper(): MovieDomainMapper = MovieDomainMapper()
}
