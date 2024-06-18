package com.sf.starwarssearch.ui.detail

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sf.starwarssearch.domain.model.PeopleDetailModel


@Stable
class PeopleDataState(
    isLoading: Boolean,
    peopleDetailResults: PeopleDetailModel? = null,
    isError: Boolean? = false
) {
    private var isLoading by mutableStateOf(isLoading)
    var peopleDetailResults by mutableStateOf(peopleDetailResults)
    private var isError by mutableStateOf(isError)
    val detailDisplay: DetailDisplay
        get() = when {
            isLoading -> DetailDisplay.Loading
            isError == true -> DetailDisplay.Error
            else -> DetailDisplay.Result
        }
}

enum class DetailDisplay {
    Loading, Result, Error
}