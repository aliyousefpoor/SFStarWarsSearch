package com.sf.starwarssearch.data

import com.sf.starwarssearch.data.datasource.StarWarsRemoteDataSource
import com.sf.starwarssearch.data.mapper.mapToModel
import com.sf.starwarssearch.domain.model.SearchResponseModel
import com.sf.starwarssearch.domain.repository.StarWarsRepository
import javax.inject.Inject

class StarWarsRepositoryImpl @Inject constructor(private val dataSource: StarWarsRemoteDataSource) :
    StarWarsRepository {
    override suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseModel {
        return dataSource.getSearchResult(keyword, page).mapToModel()
    }
}