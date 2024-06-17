package com.sf.starwarssearch.di

import com.sf.starwarssearch.data.StarWarsRepositoryImpl
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSource
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSourceImpl
import com.sf.starwarssearch.data.service.StarWarsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sf.starwarssearch.data.PeopleDetailRepositoryImpl
import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSource
import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSource
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSource
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSourceImpl
import com.sf.starwarssearch.domain.repository.PeopleDetailRepository
import com.sf.starwarssearch.domain.repository.StarWarsRepository
import com.sf.starwarssearch.domain.usecase.GetPeopleDetailUseCase
import com.sf.starwarssearch.domain.usecase.GetSearchResultUseCase
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

    @Singleton
    @Provides
    fun provideSearchResultUseCase(repository: StarWarsRepository): GetSearchResultUseCase {
        return GetSearchResultUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSpeciesRemoteDataSource(service: StarWarsService): SpeciesRemoteDataSourceImpl {
        return SpeciesRemoteDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideFilmsRemoteDataSource(service: StarWarsService): FilmsRemoteDataSourceImpl {
        return FilmsRemoteDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun providePlanetsRemoteDataSource(service: StarWarsService): PlanetRemoteDataSourceImpl {
        return PlanetRemoteDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideStarWarsRepository(
        speciesRemoteDataSource: SpeciesRemoteDataSource,
        filmsRemoteDataSource: FilmsRemoteDataSource,
        planetRemoteDataSource: PlanetRemoteDataSource
    ): PeopleDetailRepositoryImpl {
        return PeopleDetailRepositoryImpl(
            speciesRemoteDataSource = speciesRemoteDataSource,
            filmsRemoteDataSource = filmsRemoteDataSource,
            planetRemoteDataSource = planetRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun providePeopleDetailUseCase(repository: PeopleDetailRepository): GetPeopleDetailUseCase {
        return GetPeopleDetailUseCase(repository)
    }
}