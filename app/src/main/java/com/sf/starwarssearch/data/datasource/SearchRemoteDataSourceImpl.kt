package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.SearchResponseEntity
import com.sf.starwarssearch.data.service.StarWarsService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(private val service: StarWarsService) :
    SearchRemoteDataSource {
    override suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseEntity {
        return service.getSearchResult(keyword, page)
    }
}