package com.ali.starwarssearch.data.datasource

import com.ali.starwarssearch.data.model.SearchResponseEntity
import com.ali.starwarssearch.data.service.StarWarsService

class StarWarsRemoteDataSourceImpl(private val service: StarWarsService) :
    StarWarsRemoteDataSource {
    override suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseEntity {
        return service.getSearchResult(keyword, page)
    }
}