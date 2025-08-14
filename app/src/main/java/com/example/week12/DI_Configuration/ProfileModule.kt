package com.example.week12.DI_Configuration

import com.example.week12.Data.DataSource.Local.ProfileDataSource
import com.example.week12.Data.DataSource.Local.ProfileDataSourceImpl
import com.example.week12.Data.Entity.Profile
import com.example.week12.Data.Repository.ProfileRepositoryImpl
import com.example.week12.Domain.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProfileSource {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindProfileDataSource(impl: ProfileDataSourceImpl): ProfileDataSource
}
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProfileRepositoryModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindRepository(impl: ProfileRepositoryImpl): ProfileRepository
}
@Module
@InstallIn(ActivityRetainedComponent::class)
object ProfileModule {
    @Provides
    @ActivityRetainedScoped
    fun provideProfile(): Profile = Profile()
}