package com.sf.starwarssearch.ui.search

import com.sf.starwarssearch.domain.model.SearchResponseModel

sealed class SearchState {
    data class ResultState(val result: SearchResponseModel) : SearchState()
    data class LoadingState(val isLoading: Boolean) : SearchState()
    data class ErrorState(val error: String?) : SearchState()
    object EmptyState : SearchState()
}