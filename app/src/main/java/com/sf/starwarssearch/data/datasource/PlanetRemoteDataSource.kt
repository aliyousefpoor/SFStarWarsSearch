package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.PlanetEntity

interface PlanetRemoteDataSource {
    suspend fun getPlanet(url: String): PlanetEntity
}