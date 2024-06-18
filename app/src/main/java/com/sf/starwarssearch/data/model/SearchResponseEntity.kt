package com.sf.starwarssearch.data.model

import androidx.annotation.Keep

@Keep
data class SearchResponseEntity(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PeopleItemEntity>
)
