package com.sf.starwarssearch.ui.detail

import androidx.lifecycle.ViewModel
import com.sf.starwarssearch.domain.usecase.GetPeopleDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getPeopleDetailUseCase: GetPeopleDetailUseCase) :
    ViewModel() {}