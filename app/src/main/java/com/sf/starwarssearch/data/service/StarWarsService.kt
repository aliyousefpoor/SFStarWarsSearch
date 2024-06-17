package com.sf.starwarssearch.data.service

import com.sf.starwarssearch.data.model.FilmsEntity
import com.sf.starwarssearch.data.model.PlanetEntity
import com.sf.starwarssearch.data.model.SearchResponseEntity
import com.sf.starwarssearch.data.model.SpeciesEntity
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsService {

    @GET("people")
    suspend fun getSearchResult(
        @Query("search") keyword: String? = null, @Query("page") page: Int? = 1
    ): SearchResponseEntity

    @GET
    suspend fun getSpecies(@Url url: String): SpeciesEntity

    @GET
    suspend fun getFilm(@Url url: String): FilmsEntity

    @GET
    suspend fun getPlanet(@Url url: String): PlanetEntity

}