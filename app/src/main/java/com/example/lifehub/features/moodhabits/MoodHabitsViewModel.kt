package com.example.lifehub.features.moodhabits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ViewState
import com.example.lifehub.network.data.MoodEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodHabitsViewModel @Inject constructor(
    private val repo: MoodHabitsRepo
) : ViewModel() {

    private val _state = MutableStateFlow<ViewState<List<MoodEntry>>>(ViewState.Loading)
    val state: StateFlow<ViewState<List<MoodEntry>>> = _state

    init {
        getMoodEntries()
    }

    fun getMoodEntries() {
        viewModelScope.launch {
            _state.update { ViewState.Loading }
            _state.update { repo.getMoodEntries() }
        }
    }
}