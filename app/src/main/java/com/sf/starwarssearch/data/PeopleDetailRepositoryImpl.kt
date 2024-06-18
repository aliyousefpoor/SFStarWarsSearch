package com.sf.starwarssearch.data

import com.sf.starwarssearch.data.datasource.FilmsRemoteDataSource
import com.sf.starwarssearch.data.datasource.PlanetRemoteDataSource
import com.sf.starwarssearch.data.datasource.SpeciesRemoteDataSource
import com.sf.starwarssearch.data.mapper.mapToFilmsModel
import com.sf.starwarssearch.data.mapper.mapToPlanetModel
import com.sf.starwarssearch.data.mapper.mapToSpeciesModel
import com.sf.starwarssearch.data.model.FilmsEntity
import com.sf.starwarssearch.data.model.PlanetEntity
import com.sf.starwarssearch.data.model.SpeciesEntity
import com.sf.starwarssearch.domain.model.FilmsModel
import com.sf.starwarssearch.domain.model.PeopleDetailModel
import com.sf.starwarssearch.domain.model.PlanetModel
import com.sf.starwarssearch.domain.model.SpeciesModel
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
        planetUrl: String?
    ): PeopleDetailModel = withContext(Dispatchers.IO) {
        val speciesList = ArrayList<SpeciesModel>()
        val filmsList = ArrayList<FilmsModel>()
        val planetList = ArrayList<PlanetModel>()
        val specifics = async {
            speciesUrls?.forEach {
                val specieData = getSpecies(it).mapToSpeciesModel()
                speciesList.add(specieData)
            }
        }

        val films = async {
            filmsUrls?.forEach {
                val filmData = getFilms(it).mapToFilmsModel()
                filmsList.add(filmData)
            }
        }

        val planets = async {
            planetUrl?.let {
                val planetData = getPlanet(it).mapToPlanetModel()
                planetList.add(planetData)
            }
        }

        listOf(specifics, films, planets).awaitAll()
        PeopleDetailModel(
            species = speciesList,
            films = filmsList,
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