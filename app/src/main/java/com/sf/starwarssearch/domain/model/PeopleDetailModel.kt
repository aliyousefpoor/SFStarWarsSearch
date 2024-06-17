package com.sf.starwarssearch.domain.model

import com.sf.starwarssearch.data.model.FilmsEntity
import com.sf.starwarssearch.data.model.PlanetEntity
import com.sf.starwarssearch.data.model.SpeciesEntity

data class PeopleDetailModel(
    val speciesEntities: List<SpeciesEntity>?,
    val planetPopulation: List<PlanetEntity>?,
    val filmsEntities: List<FilmsEntity>?
)