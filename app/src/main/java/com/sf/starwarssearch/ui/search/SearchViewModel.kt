package com.sf.starwarssearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.domain.model.SearchResponseModel
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
    private val peopleList = ArrayList<PeopleItemModel>()
    private var isHaveNextPage = true
    private var page = 1
    var query: String? = null


    fun handleSearchIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.Search -> {
                if (query != intent.query) {
                    resetSearchData()
                }
                if (isHaveNextPage || query != intent.query) getSearchResult(intent.query)
            }


            SearchIntent.Loading -> {}
            SearchIntent.NoResultFound -> {}
        }
    }

    private fun getSearchResult(keyword: String) {
        viewModelScope.launch {
            if (page == 1) _state.value = SearchState.LoadingState(true)
            try {
                val result = getSearchResultUseCase.getSearchResult(keyword, page)
                updateSearchData(result, keyword)
                if (result.count == 0) _state.value =
                    SearchState.EmptyState else _state.value =
                    SearchState.ResultState(result = result.copy(results = peopleList))
            } catch (e: Exception) {
                _state.value = SearchState.ErrorState(error = e.message)
            }
        }
    }

    private fun updateSearchData(result: SearchResponseModel, keyword: String) {
        query = keyword
        isHaveNextPage = !result.next.isNullOrEmpty()
        page = if (isHaveNextPage) page + 1 else page
        peopleList.addAll(result.results)
    }

    private fun resetSearchData() {
        isHaveNextPage = true
        peopleList.clear()
        page = 1
    }
}