package com.example.lifehub.features.questionaire.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.PostResult
import com.example.lifehub.features.questionaire.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DobViewModel @Inject constructor(
    private val repo: UserRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // nav args
    private val firstName: String = checkNotNull(savedStateHandle["firstName"])
    private val lastName: String = checkNotNull(savedStateHandle["lastName"])

    private val _saveUserProfileResult = MutableStateFlow<PostResult<Unit>?>(null)
    val saveUserProfileResult: StateFlow<PostResult<Unit>?> = _saveUserProfileResult

    val isLoading = MutableStateFlow<Boolean>(false)

    fun savedUserProfile(
        dob: Long
    ) {
        viewModelScope.launch {
            isLoading.value = true
            _saveUserProfileResult.value = repo.saveUserProfile(firstName, lastName, dob)
            isLoading.value = false
        }
    }
}