package com.sf.starwarssearch.domain.model

data class PeopleDetailModel(
    val speciesEntities: List<SpeciesModel>?,
    val planetPopulation: List<PlanetModel>?,
    val filmsEntities: List<FilmsModel>?
)