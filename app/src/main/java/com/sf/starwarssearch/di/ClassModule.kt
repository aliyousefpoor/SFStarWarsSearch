package com.sf.starwarssearch.di

import com.sf.starwarssearch.data.StarWarsRepositoryImpl
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSource
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSourceImpl
import com.sf.starwarssearch.data.service.StarWarsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClassModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson: Gson? = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl("https://swapi.dev/api/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): StarWarsService {
        return retrofit.create(StarWarsService::class.java)
    }

    @Singleton
    @Provides
    fun provideStarWarsRemoteDataSource(service: StarWarsService): StarWarsRemoteDataSourceImpl {
        return StarWarsRemoteDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideStarWarsRepository(dataSource: StarWarsRemoteDataSource): StarWarsRepositoryImpl {
        return StarWarsRepositoryImpl(dataSource)
    }
}