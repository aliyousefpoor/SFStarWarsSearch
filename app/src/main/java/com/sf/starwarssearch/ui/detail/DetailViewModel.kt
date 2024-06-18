package com.sf.starwarssearch.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sf.starwarssearch.domain.usecase.GetPeopleDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getPeopleDetailUseCase: GetPeopleDetailUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<PeopleDataState?>(
        PeopleDataState(
            isLoading = true,
            peopleDetailResults = null,
            isError = false
        )
    )
    val state: StateFlow<PeopleDataState?> = _state

    fun getPeopleDetail(
        speciesUrl: List<String>?,
        filmsUrl: List<String>?,
        planetsUrl: String?
    ) {
        viewModelScope.launch {
            try {
                val result =
                    getPeopleDetailUseCase.getPeopleDetail(speciesUrl, filmsUrl, planetsUrl)
                result.let {
                    _state.update {
                        PeopleDataState(
                            isLoading = false,
                            peopleDetailResults = result,
                            isError = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    PeopleDataState(
                        isLoading = false,
                        peopleDetailResults = null,
                        isError = true
                    )
                }
            }
        }
    }
}