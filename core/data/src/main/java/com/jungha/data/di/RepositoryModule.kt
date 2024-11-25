package com.jungha.data.di

import com.jungha.data.impl.MediaRepositoryImpl
import com.jungha.domain.repository.MediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindsMediaRepositoryImpl(
        repository: MediaRepositoryImpl
    ): MediaRepository
}