package com.sf.starwarssearch.data.model

import androidx.annotation.Keep

@Keep
data class FilmsEntity(
    val title: String?,
    val opening_crawl: String?
)