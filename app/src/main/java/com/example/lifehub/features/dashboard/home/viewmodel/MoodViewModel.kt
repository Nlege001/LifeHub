package com.example.lifehub.features.dashboard.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.PostResult
import com.example.core.data.ViewState
import com.example.lifehub.features.dashboard.home.data.Mood
import com.example.lifehub.features.dashboard.home.repo.MoodRepo
import com.example.lifehub.network.data.MoodEntry
import com.google.firebase.firestore.core.View
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val repo: MoodRepo
) : ViewModel() {

    private val _moods = MutableStateFlow<ViewState<List<MoodEntry>>>(ViewState.Loading)
    val moods: StateFlow<ViewState<List<MoodEntry>>> = _moods

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _postResult = MutableStateFlow<PostResult<Unit>?>(null)
    val postResult: StateFlow<PostResult<Unit>?> = _postResult

    init {
        getMoods()
    }

    fun getMoods() {
        viewModelScope.launch {
            _moods.value = ViewState.Loading
            _moods.value = repo.getMoodEntries()
        }
    }

    fun saveMood(
        mood: Mood,
        reflection: String?,
        intensity: Float,
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _postResult.value = repo.saveMood(mood, reflection, intensity)
            _isLoading.value = false
            getMoods()
        }
    }
}