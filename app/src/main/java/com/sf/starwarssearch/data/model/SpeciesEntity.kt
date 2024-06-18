package com.sf.starwarssearch.data.model

import androidx.annotation.Keep

@Keep
data class SpeciesEntity(
    val name: String?,
    val language: String?,
    val homeworld: String
)