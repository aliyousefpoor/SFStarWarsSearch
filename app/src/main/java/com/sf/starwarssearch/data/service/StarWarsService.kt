package com.sf.starwarssearch.data.service

import com.sf.starwarssearch.data.model.SearchResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsService {

    @GET("people")
    suspend fun getSearchResult(
        @Query("search") keyword: String? = null,
        @Query("page") page: Int? = 1
    ): SearchResponseEntity

}