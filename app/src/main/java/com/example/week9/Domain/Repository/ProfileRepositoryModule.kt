package com.example.week9.Domain.Repository

import com.example.week9.Domain.Data.ProfileParametersData
import com.example.week9.Domain.FilmsRepository
import com.example.week9.Domain.ProfileParameters
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(impl: ProfileParametersImpl): ProfileParameters
}