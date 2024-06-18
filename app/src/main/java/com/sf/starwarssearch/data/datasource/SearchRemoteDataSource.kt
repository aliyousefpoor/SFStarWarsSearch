package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.SearchResponseEntity

interface SearchRemoteDataSource {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseEntity
}