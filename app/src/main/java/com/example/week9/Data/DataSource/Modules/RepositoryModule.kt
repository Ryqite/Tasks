package com.example.week9.Data.DataSource.Modules

import com.example.week9.Data.Repository.FilmsRepositoryImpl
import com.example.week9.Data.Repository.ProfileParametersImpl
import com.example.week9.Domain.FilmsRepository
import com.example.week9.Domain.ProfileParameters
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(impl: FilmsRepositoryImpl): FilmsRepository
}
@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(impl: ProfileParametersImpl): ProfileParameters
}