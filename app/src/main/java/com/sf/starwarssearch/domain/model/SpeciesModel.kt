package com.sf.starwarssearch.domain.model

import androidx.annotation.Keep

@Keep
data class SpeciesModel(
    val name: String?,
    val language: String?,
    val homeWorld: String?
)