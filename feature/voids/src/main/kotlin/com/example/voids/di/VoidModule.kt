package com.example.voids.di

import com.example.voids.data.VoidRepositoryImpl
import com.example.voids.data.VoidRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VoidModule {

    @Binds
    @Singleton
    abstract fun bindVoidRepository(
        voidRepositoryImpl: VoidRepositoryImpl
    ): VoidRepository
}


