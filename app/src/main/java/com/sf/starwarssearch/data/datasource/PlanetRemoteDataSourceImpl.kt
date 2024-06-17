package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.PlanetEntity
import com.sf.starwarssearch.data.service.StarWarsService
import javax.inject.Inject

class PlanetRemoteDataSourceImpl @Inject constructor(private val service: StarWarsService) :
    PlanetRemoteDataSource {
    override suspend fun getPlanet(url: String): PlanetEntity {
        return service.getPlanet(url)
    }
}