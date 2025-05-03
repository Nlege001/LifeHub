package com.example.lifehub.features.dashboard.sidemenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SideMenuViewModel @Inject constructor(
    private val repo: SideMenuRepo
) : ViewModel() {

    private val _data = MutableStateFlow<ViewState<SideMenuData>>(ViewState.Loading)
    val data: MutableStateFlow<ViewState<SideMenuData>> = _data

    fun getData() {
        viewModelScope.launch {
            _data.value = repo.getSideMenuData()
        }
    }
}