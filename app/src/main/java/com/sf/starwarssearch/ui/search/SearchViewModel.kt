package com.sf.starwarssearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.domain.model.SearchResponseModel
import com.sf.starwarssearch.domain.usecase.GetSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchResultUseCase: GetSearchResultUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(
        SearchState(
            query = null,
            isLoading = false,
            isError = false,
            searchResults = null
        )
    )

    val state: StateFlow<SearchState> = _state
    private val peopleList = ArrayList<PeopleItemModel>()
    private var hasNextPage = true
    private var page = 1


    fun getSearchResult(keyword: String) {
        if ((keyword == _state.value.query && hasNextPage) || keyword != _state.value.query) {
            if (keyword != _state.value.query) resetSearchData()
            viewModelScope.launch {
                if (page == 1)
                    _state.update { currentState ->
                        SearchState(
                            query = keyword,
                            isError = currentState.isError,
                            searchResults = currentState.searchResults,
                            isLoading = true
                        )
                    }
                try {
                    val result = getSearchResultUseCase.getSearchResult(keyword, page)
                    updateFields(result)
                    _state.update { currentState ->
                        SearchState(
                            isError = currentState.isError,
                            searchResults = result?.copy(results = peopleList),
                            isLoading = false,
                            query = currentState.query
                        )
                    }

                } catch (e: Exception) {
                    _state.update { currentState ->
                        SearchState(
                            isError = true,
                            searchResults = null,
                            isLoading = false,
                            query = currentState.query
                        )
                    }
                }
            }
        }
    }

    private fun updateFields(
        result: SearchResponseModel?
    ) {
        hasNextPage = !result?.next.isNullOrEmpty()
        page = if (hasNextPage) page + 1 else page
        result?.let { peopleList.addAll(it.results) }
    }

    private fun resetSearchData() {
        hasNextPage = true
        peopleList.clear()
        page = 1
    }
}