package com.sf.starwarssearch.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sf.starwarssearch.domain.model.PeopleDetailModel
import com.sf.starwarssearch.domain.usecase.GetPeopleDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getPeopleDetailUseCase: GetPeopleDetailUseCase) :
    ViewModel() {


    private val _state = MutableStateFlow<PeopleDetailModel?>(null)
    val state: StateFlow<PeopleDetailModel?> = _state


    fun getPeopleDetail(
        speciesUrl: List<String>?,
        filmsUrl: List<String>?,
        planetsUrl: String?
    ) {
        viewModelScope.launch {
            val result = getPeopleDetailUseCase.getPeopleDetail(speciesUrl, filmsUrl, planetsUrl)
            result?.let {
                _state.value = it
            }
        }
    }
}