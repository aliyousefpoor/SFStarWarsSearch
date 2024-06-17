package com.ali.starwarssearch.data.datasource

import com.ali.starwarssearch.data.model.SearchResponseEntity

interface StarWarsRemoteDataSource {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseEntity
}