package com.example.authorization.di

import com.example.authorization.data.AuthorizationRepositoryImpl
import com.example.authorization.data.AuthorizationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthorizationModule {

    @Binds
    @Singleton
    abstract fun bindAuthorizationRepository(
        authorizationRepositoryImpl: AuthorizationRepositoryImpl
    ): AuthorizationRepository
}

