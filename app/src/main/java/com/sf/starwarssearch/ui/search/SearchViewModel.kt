package com.sf.starwarssearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.domain.usecase.GetSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchResultUseCase: GetSearchResultUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.LoadingState(false))
    val state: StateFlow<SearchState> = _state
    var page = 1


    fun handleSearchIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.Search -> {
                getSearchResult(intent.query)
            }

            SearchIntent.Loading -> {}
            SearchIntent.NoResultFound -> {}
        }
    }

    private fun getSearchResult(keyword: String) {
        viewModelScope.launch {
            _state.value = SearchState.LoadingState(true)
            try {
                val result = getSearchResultUseCase.getSearchResult(keyword, page)
                if (result.count == 0) _state.value =
                    SearchState.EmptyState else _state.value =
                    SearchState.ResultState(result = result)
            } catch (e: Exception) {
                _state.value = SearchState.ErrorState(error = e.message)
            }
        }
    }
}