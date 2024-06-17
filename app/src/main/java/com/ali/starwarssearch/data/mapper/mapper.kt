package com.ali.starwarssearch.data.mapper

import com.ali.starwarssearch.data.model.PeopleItemEntity
import com.ali.starwarssearch.data.model.SearchResponseEntity
import com.ali.starwarssearch.domain.model.PeopleItemModel
import com.ali.starwarssearch.domain.model.SearchResponseModel

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