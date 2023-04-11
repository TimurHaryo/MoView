package com.timtam.initial.di

import com.timtam.initial.InitialEntryPoint
import com.timtam.initial_contract.navigation.InitialFeatureDirection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InitialModule {

    @Binds
    abstract fun bindInitialFeatureDirection(initialEntryPoint: InitialEntryPoint): InitialFeatureDirection
}
