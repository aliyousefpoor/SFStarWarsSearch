package com.sf.starwarssearch.domain.model

import androidx.annotation.Keep

@Keep
data class SearchResponseModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PeopleItemModel>
)
