package com.example.activation.di

import com.example.activation.data.repository.ActivationRepositoryImpl
import com.example.activation.domain.repository.ActivationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivationModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        activationRepositoryImpl: ActivationRepositoryImpl
    ): ActivationRepository
}

