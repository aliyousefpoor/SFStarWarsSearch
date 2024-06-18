package com.sf.starwarssearch.ui.search

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sf.starwarssearch.domain.model.SearchResponseModel

@Stable
class SearchState(
    query: String? = null,
    isLoading: Boolean = false,
    isError: Boolean = false,
    searchResults: SearchResponseModel? = null
) {
    var query by mutableStateOf(query)
    var isLoading by mutableStateOf(isLoading)
    var searchResults by mutableStateOf(searchResults)
    var isError by mutableStateOf(isError)
    val searchDisplay: SearchDisplay
        get() = when {
            isLoading == true -> SearchDisplay.Loading
            isError == true -> SearchDisplay.Error
            searchResults?.count == 0 && searchResults?.results?.isEmpty() == true -> SearchDisplay.NoResult
            else -> SearchDisplay.Result
        }
}

enum class SearchDisplay {
    Loading, Result, Error, NoResult
}