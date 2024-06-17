package com.ali.starwarssearch.domain.usecase

import com.ali.starwarssearch.domain.model.SearchResponseModel
import com.ali.starwarssearch.domain.repository.StarWarsRepository

class GetSearchResultUseCase(private val repository: StarWarsRepository) {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseModel {
        return repository.getSearchResult(keyword, page)
    }
}