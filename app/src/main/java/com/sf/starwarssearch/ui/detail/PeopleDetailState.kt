package com.sf.starwarssearch.ui.detail

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sf.starwarssearch.domain.model.PeopleDetailModel

@Stable
class PeopleDetailState(
    searching: Boolean,
    peopleDetailResults: PeopleDetailModel?
) {
    var searching by mutableStateOf(searching)
    var peopleDetailResults by mutableStateOf(peopleDetailResults)
}