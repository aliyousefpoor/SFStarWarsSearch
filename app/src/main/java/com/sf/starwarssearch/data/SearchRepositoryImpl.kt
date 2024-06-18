package com.sf.starwarssearch.data

import com.sf.starwarssearch.data.datasource.SearchRemoteDataSource
import com.sf.starwarssearch.data.mapper.mapToModel
import com.sf.starwarssearch.domain.model.SearchResponseModel
import com.sf.starwarssearch.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val dataSource: SearchRemoteDataSource) :
    SearchRepository {
    override suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseModel {
        return dataSource.getSearchResult(keyword, page).mapToModel()
    }
}