package com.example.lifehub.features.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardFeedViewModel @Inject constructor(
    private val repo: DashboardFeedRepo
) : ViewModel() {

    private val _feedData = MutableStateFlow<ViewState<DashboardFeedData>>(ViewState.Loading)
    val feedData: StateFlow<ViewState<DashboardFeedData>> = _feedData

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _feedData.value = ViewState.Loading
            _feedData.value = repo.getData()
        }
    }
}