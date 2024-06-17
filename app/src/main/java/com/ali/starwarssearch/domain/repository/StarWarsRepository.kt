package com.ali.starwarssearch.domain.repository

import com.ali.starwarssearch.domain.model.SearchResponseModel

interface StarWarsRepository {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseModel
}