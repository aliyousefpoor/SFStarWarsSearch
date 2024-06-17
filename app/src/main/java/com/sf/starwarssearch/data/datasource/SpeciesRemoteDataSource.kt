package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.SpeciesEntity

interface SpeciesRemoteDataSource {
    suspend fun getSpecies(url: String): SpeciesEntity
}