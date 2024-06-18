package com.sf.starwarssearch.di

import com.sf.starwarssearch.data.PeopleDetailRepositoryImpl
import com.sf.starwarssearch.data.SearchRepositoryImpl
import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSource
import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSource
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSource
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.SearchRemoteDataSource
import com.sf.starwarssearch.data.datasource.SearchRemoteDataSourceImpl
import com.sf.starwarssearch.domain.repository.PeopleDetailRepository
import com.sf.starwarssearch.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Singleton
    @Binds
    abstract fun bindStarWarsRemoteDataSource(remoteDataSourceImpl: SearchRemoteDataSourceImpl): SearchRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindStarWarsRepository(starWarsRepositoryImpl: SearchRepositoryImpl): SearchRepository


    @Singleton
    @Binds
    abstract fun bindSpeciesRemoteDataSource(remoteDataSourceImpl: SpeciesRemoteDataSourceImpl): SpeciesRemoteDataSource


    @Singleton
    @Binds
    abstract fun bindFilmRemoteDataSource(remoteDataSourceImpl: FilmsRemoteDataSourceImpl): FilmsRemoteDataSource


    @Singleton
    @Binds
    abstract fun bindPlanetRemoteDataSource(remoteDataSourceImpl: PlanetRemoteDataSourceImpl): PlanetRemoteDataSource


    @Singleton
    @Binds
    abstract fun bindPeopleDetailRepository(peopleDetailRepositoryImpl: PeopleDetailRepositoryImpl): PeopleDetailRepository
}