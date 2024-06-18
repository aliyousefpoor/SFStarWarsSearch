package com.sf.starwarssearch.domain.model

import androidx.annotation.Keep

@Keep
data class FilmsModel(
    val title: String?,
    val openingCrawl: String?
)