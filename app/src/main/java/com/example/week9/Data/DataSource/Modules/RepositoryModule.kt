package com.example.week9.Data.DataSource.Modules

import com.example.week9.Data.Repository.FilmsRepositoryImpl
import com.example.week9.Data.Repository.ProfileParametersImpl
import com.example.week9.Domain.FilmsRepository
import com.example.week9.Domain.ProfileParameters
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
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
@InstallIn(ActivityRetainedComponent::class)
abstract class ProfileRepositoryModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindRepository(impl: ProfileParametersImpl): ProfileParameters
}