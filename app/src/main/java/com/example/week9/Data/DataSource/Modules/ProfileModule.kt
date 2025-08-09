package com.example.week9.Data.DataSource.Modules

import com.example.week9.Data.DTO.Profile
import com.example.week9.Data.DataSource.Remote.ProfileDataSource
import com.example.week9.Data.DataSource.Remote.ProfileDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object ProfileModule {
    @Provides
    @ActivityRetainedScoped
    fun provideProfile():Profile = Profile()
}