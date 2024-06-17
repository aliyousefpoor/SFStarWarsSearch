package com.sf.starwarssearch.domain.repository

import com.sf.starwarssearch.domain.model.SearchResponseModel

interface StarWarsRepository {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseModel
}