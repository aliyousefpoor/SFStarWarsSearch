package com.sf.starwarssearch.data.datasource

import com.sf.starwarssearch.data.model.FilmsEntity

interface FilmsRemoteDataSource {
    suspend fun getFilms(url: String): FilmsEntity
}