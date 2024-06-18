package com.sf.starwarssearch.domain.usecase

import com.sf.starwarssearch.domain.model.PeopleDetailModel
import com.sf.starwarssearch.domain.repository.PeopleDetailRepository
import javax.inject.Inject

class GetPeopleDetailUseCase @Inject constructor(private val repository: PeopleDetailRepository) {
    suspend fun getPeopleDetail(
        speciesUrls: List<String>?,
        filmsUrls: List<String>?,
        planetUrl: String?
    ): PeopleDetailModel {
        return repository.getPeopleDetail(speciesUrls, filmsUrls, planetUrl)
    }
}