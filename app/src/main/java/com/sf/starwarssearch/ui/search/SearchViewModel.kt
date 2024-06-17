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

    private val _state = MutableStateFlow<List<PeopleItemModel>?>(null)
    val state: StateFlow<List<PeopleItemModel>?> = _state
    var page = 1

    fun getSearchResult(keyword: String) {
        viewModelScope.launch {
            try {
                val result = getSearchResultUseCase.getSearchResult(keyword, page)
                _state.value = result.results
            } catch (e: Exception) {
            }
        }
    }
}