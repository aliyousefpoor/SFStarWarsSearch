package com.sf.starwarssearch.data.mapper

import com.sf.starwarssearch.data.model.FilmsEntity
import com.sf.starwarssearch.data.model.PeopleItemEntity
import com.sf.starwarssearch.data.model.PlanetEntity
import com.sf.starwarssearch.data.model.SearchResponseEntity
import com.sf.starwarssearch.data.model.SpeciesEntity
import com.sf.starwarssearch.domain.model.FilmsModel
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.domain.model.PlanetModel
import com.sf.starwarssearch.domain.model.SearchResponseModel
import com.sf.starwarssearch.domain.model.SpeciesModel

fun SearchResponseEntity.mapToModel(): SearchResponseModel {
    return SearchResponseModel(
        count = count,
        next = next,
        previous = previous,
        results = results.map { it.mapToPeopleModel() }
    )
}

fun PeopleItemEntity.mapToPeopleModel(): PeopleItemModel {
    return PeopleItemModel(
        name = name,
        height = height,
        mass = mass,
        hair_color = hair_color,
        skin_color = skin_color,
        eye_color = eye_color,
        birth_year = birth_year,
        gender = gender,
        homeworld = homeworld,
        films = films,
        species = species,
        vehicles = vehicles,
        starships = starships,
        created = created,
        edited = edited,
        url = url
    )
}

fun SpeciesEntity.mapToSpeciesModel(): SpeciesModel {
    return SpeciesModel(name = name, language = language, homeworld = homeworld)
}

fun FilmsEntity.mapToFilmsModel(): FilmsModel {
    return FilmsModel(title = title, opening_crawl = opening_crawl)
}

fun PlanetEntity.mapToPlanetModel(): PlanetModel {
    return PlanetModel(population = population)
}