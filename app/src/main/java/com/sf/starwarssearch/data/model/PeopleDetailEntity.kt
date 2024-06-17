package com.sf.starwarssearch.data.model

data class PeopleDetailEntity(
    val speciesNames: List<String>?,
    val speciesLanguage: String?,
    val speciesHomeWorld: String?,
    val planetPopulation: String?,
    val filmsName: List<String>?,
    val openingCrawl: String
)