package com.ali.starwarssearch.data.model

data class SearchResponseEntity(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PeopleItemEntity>
)
