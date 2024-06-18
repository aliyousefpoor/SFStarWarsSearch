package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.FilmsEntity
import com.sf.starwarssearch.data.service.StarWarsService
import javax.inject.Inject

class FilmsRemoteDataSourceImpl @Inject constructor(private val service: StarWarsService) :
    FilmsRemoteDataSource {
    override suspend fun getFilms(url: String): FilmsEntity {
        return service.getFilm(url)
    }
}