package com.sf.starwarssearch.domain.model

data class PeopleDetailModel(
    val species: List<SpeciesModel>?,
    val planetPopulation: List<PlanetModel>?,
    val films: List<FilmsModel>?
)