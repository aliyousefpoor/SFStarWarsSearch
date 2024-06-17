package com.ali.starwarssearch.data.service

import com.ali.starwarssearch.data.model.SearchResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsService {

    @GET("https://swapi.dev/api/people")
    suspend fun getSearchResult(
        @Query("search") keyword: String? = null,
        @Query("page") page: Int? = 1
    ): SearchResponseEntity

}