package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.SearchResponseEntity

interface StarWarsRemoteDataSource {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseEntity
}