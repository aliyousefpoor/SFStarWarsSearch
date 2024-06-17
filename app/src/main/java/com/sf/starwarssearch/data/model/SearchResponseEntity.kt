package com.sf.starwarssearch.data.model

import com.sf.starwarssearch.data.model.PeopleItemEntity

data class SearchResponseEntity(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PeopleItemEntity>
)
