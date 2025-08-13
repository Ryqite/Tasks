package com.example.week12.DI_Configuration

import com.example.week12.Data.DataSource.Remote.BooksAPI
import com.example.week12.Data.DataSource.Remote.RemoteDataSource
import com.example.week12.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week12.Data.DataSource.Remote.RetryInterceptor
import com.example.week12.Data.Repository.BooksNetworkRepositoryImpl
import com.example.week12.Data.Utils.Constants.Companion.BASE_URL
import com.example.week12.Domain.BooksNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSource{
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}
@Module
@InstallIn(SingletonComponent::class)
abstract class BooksRepository{
    @Binds
    @Singleton
    abstract fun bindBooksRepository(impl: BooksNetworkRepositoryImpl): BooksNetworkRepository
}
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
    return OkHttpClient.Builder()
        .addInterceptor(RetryInterceptor(3))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =HttpLoggingInterceptor.Level.BODY
        }).build()
    }
    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideBooksAPI(retrofit: Retrofit): BooksAPI{
        return retrofit.create(BooksAPI::class.java)
    }
}