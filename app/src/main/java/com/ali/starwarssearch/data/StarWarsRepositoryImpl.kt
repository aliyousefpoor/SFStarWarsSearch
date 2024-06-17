package com.ali.starwarssearch.data

import com.ali.starwarssearch.data.datasource.StarWarsRemoteDataSource
import com.ali.starwarssearch.data.model.SearchResponseEntity

class StarWarsRepositoryImpl(private val dataSource: StarWarsRemoteDataSource) {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseEntity {
        return dataSource.getSearchResult(keyword, page)
    }
}