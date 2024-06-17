package com.sf.starwarssearch.di

import com.sf.starwarssearch.data.StarWarsRepositoryImpl
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSource
import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSourceImpl
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
}