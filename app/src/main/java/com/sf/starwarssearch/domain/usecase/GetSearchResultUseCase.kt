package com.sf.starwarssearch.domain.usecase

import com.sf.starwarssearch.domain.model.SearchResponseModel
import com.sf.starwarssearch.domain.repository.StarWarsRepository
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(private val repository: StarWarsRepository) {
    suspend fun getSearchResult(keyword: String?, page: Int?): SearchResponseModel {
        return repository.getSearchResult(keyword, page)
    }
}