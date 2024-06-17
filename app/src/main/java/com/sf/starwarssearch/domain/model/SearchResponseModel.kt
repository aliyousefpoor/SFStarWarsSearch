package com.sf.starwarssearch.domain.model

data class SearchResponseModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PeopleItemModel>
)
