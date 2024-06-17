package com.ali.starwarssearch.data

import com.ali.starwarssearch.data.datasource.StarWarsRemoteDataSource
import com.ali.starwarssearch.data.mapper.mapToModel
import com.ali.starwarssearch.domain.model.SearchResponseModel
import com.ali.starwarssearch.domain.repository.StarWarsRepository

class StarWarsRepositoryImpl(private val dataSource: StarWarsRemoteDataSource) :
    StarWarsRepository {
    override suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseModel {
        return dataSource.getSearchResult(keyword, page).mapToModel()
    }
}