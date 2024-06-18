package com.sf.starwarssearch.domain.model

import androidx.annotation.Keep

@Keep
data class PeopleDetailModel(
    val species: List<SpeciesModel>?,
    val planetPopulation: List<PlanetModel>?,
    val films: List<FilmsModel>?
)