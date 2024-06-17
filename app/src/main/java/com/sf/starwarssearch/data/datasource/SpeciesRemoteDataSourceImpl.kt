package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.SpeciesEntity
import com.sf.starwarssearch.data.service.StarWarsService
import javax.inject.Inject

class SpeciesRemoteDataSourceImpl @Inject constructor(private val service: StarWarsService) :
    SpeciesRemoteDataSource {
    override suspend fun getSpecies(url: String): SpeciesEntity {
        return service.getSpecies(url)
    }
}