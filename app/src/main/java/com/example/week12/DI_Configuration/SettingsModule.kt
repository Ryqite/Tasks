package com.example.week12.DI_Configuration

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.week12.Data.DataSource.Local.AppDataStore
import com.example.week12.Data.DataSource.Local.DataStoreSource
import com.example.week12.Data.DataSource.Local.DataStoreSourceImpl
import com.example.week12.Data.DataSource.Local.ProfileDataSource
import com.example.week12.Data.DataSource.Local.ProfileDataSourceImpl
import com.example.week12.Data.DataSource.Local.dataStore
import com.example.week12.Data.Entity.Profile
import com.example.week12.Data.Repository.ProfileRepositoryImpl
import com.example.week12.Data.Repository.SettingsRepositoryImpl
import com.example.week12.Domain.ProfileRepository
import com.example.week12.Domain.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreSource {
    @Binds
    @Singleton
    abstract fun bindDataStoreSource(impl: DataStoreSourceImpl): DataStoreSource
}
@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences>{
        return context.dataStore
    }
    @Provides
    @Singleton
    fun provideAppDataStore(dataStore: DataStore<Preferences>): AppDataStore {
        return AppDataStore(dataStore)
    }
}