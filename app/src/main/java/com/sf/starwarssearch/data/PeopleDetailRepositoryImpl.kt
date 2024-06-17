package com.sf.starwarssearch.data

import android.media.Image.Plane
import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSource
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSource
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSource
import com.sf.starwarssearch.data.model.FilmsEntity
import com.sf.starwarssearch.data.model.PlanetEntity
import com.sf.starwarssearch.data.model.SpeciesEntity
import com.sf.starwarssearch.domain.model.PeopleDetailModel
import com.sf.starwarssearch.domain.repository.PeopleDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PeopleDetailRepositoryImpl @Inject constructor(
    private val speciesRemoteDataSource: SpeciesRemoteDataSource,
    private val filmsRemoteDataSource: FilmsRemoteDataSource,
    private val planetRemoteDataSource: PlanetRemoteDataSource
) : PeopleDetailRepository {
    override suspend fun getPeopleDetail(
        speciesUrls: List<String>?,
        filmsUrls: List<String>?,
        planetUrls: List<String>?
    ) = withContext(Dispatchers.IO) {
        val speciesList = ArrayList<SpeciesEntity>()
        val filmsList = ArrayList<FilmsEntity>()
        val planetList = ArrayList<PlanetEntity>()
        val specifics = async {
            speciesUrls?.forEach {
                val specieData = getSpecies(it)
                speciesList.add(specieData)
            }
        }

        val films = async {
            filmsUrls?.forEach {
                val filmData = getFilms(it)
                filmsList.add(filmData)
            }
        }

        val planets = async {
            planetUrls?.forEach {
                val planetData = getPlanet(it)
                planetList.add(planetData)
            }
        }

        listOf(specifics, films, planets).awaitAll()
        PeopleDetailModel(
            speciesEntities = speciesList,
            filmsEntities = filmsList,
            planetPopulation = planetList
        )
    }


    private suspend fun getSpecies(url: String): SpeciesEntity {
        return speciesRemoteDataSource.getSpecies(url)
    }

    private suspend fun getFilms(url: String): FilmsEntity {
        return filmsRemoteDataSource.getFilms(url)
    }

    private suspend fun getPlanet(url: String): PlanetEntity {
        return planetRemoteDataSource.getPlanet(url)
    }
}