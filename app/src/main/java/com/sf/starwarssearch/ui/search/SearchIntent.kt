package com.sf.starwarssearch.ui.search

sealed class SearchIntent {
    data class Search(val query: String) : SearchIntent()
    object Loading : SearchIntent()
    object NoResultFound : SearchIntent()
}