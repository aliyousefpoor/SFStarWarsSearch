package com.sf.starwarssearch.domain.repository

import com.sf.starwarssearch.domain.model.PeopleDetailModel

interface PeopleDetailRepository {
    suspend fun getPeopleDetail(
        speciesUrls: List<String>?,
        filmsUrls: List<String>?,
        planetUrls: List<String>?
    ): PeopleDetailModel
}