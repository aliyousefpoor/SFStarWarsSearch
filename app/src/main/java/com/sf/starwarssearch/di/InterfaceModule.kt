package com.sf.starwarssearch.di

import com.sf.starwarssearch.data.PeopleDetailRepositoryImpl
import com.sf.starwarssearch.data.StarWarsRepositoryImpl
import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSource
import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSource
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSource
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSourceImpl
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSource
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSourceImpl
import com.sf.starwarssearch.domain.repository.PeopleDetailRepository
import com.sf.starwarssearch.domain.repository.StarWarsRepository
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
    abstract fun bindStarWarsRemoteDataSource(remoteDataSourceImpl: StarWarsRemoteDataSourceImpl): StarWarsRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindStarWarsRepository(starWarsRepositoryImpl: StarWarsRepositoryImpl): StarWarsRepository


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