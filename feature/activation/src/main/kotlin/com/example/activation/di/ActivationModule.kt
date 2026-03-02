package com.example.activation.di

import com.example.activation.data.repository.ActivationRepository
import com.example.activation.data.repository.ActivationRepositoryImpl
import com.example.activation.data.remote.ActivationApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivationModule {

    @Binds
    @Singleton
    abstract fun bindActivationRepository(
        activationRepositoryImpl: ActivationRepositoryImpl
    ): ActivationRepository

    companion object {

        @Provides
        @Singleton
        fun provideActivationApiService(retrofit: Retrofit): ActivationApiService =
            retrofit.create(ActivationApiService::class.java)
    }
}
