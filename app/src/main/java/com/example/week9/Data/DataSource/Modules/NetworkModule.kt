package com.example.week9.Data.DataSource.Modules

import com.example.week9.Data.DataSource.Remote.FilmsAPI
import com.example.week9.Data.DataSource.Remote.ProfileDataSource
import com.example.week9.Data.DataSource.Remote.ProfileDataSourceImpl
import com.example.week9.Data.DataSource.Remote.RemoteDataSource
import com.example.week9.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week9.Data.DataSource.Remote.RetryInterceptor
import com.example.week9.Data.Utils.Constants.Companion.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSource {
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProfileSource {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindProfileDataSource(impl: ProfileDataSourceImpl): ProfileDataSource
}
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(RetryInterceptor(maxRetries = 3))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
    @Provides
    @Singleton
    fun provideRetrofitClient(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    @Provides
    @Singleton
    fun provideFilmsAPI(retrofit: Retrofit): FilmsAPI {
        return retrofit.create(FilmsAPI::class.java)
    }
}