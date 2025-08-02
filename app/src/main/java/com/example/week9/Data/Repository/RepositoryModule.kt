package com.example.week9.Data.Repository

import com.example.week9.Data.DataSource.Remote.RemoteDataSource
import com.example.week9.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week9.Domain.FilmsRepository
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