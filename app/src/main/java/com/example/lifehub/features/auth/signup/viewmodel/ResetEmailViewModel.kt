package com.example.lifehub.features.auth.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.PostResult
import com.example.lifehub.features.auth.signup.repo.ResetEmailRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetEmailViewModel @Inject constructor(
    private val repo: ResetEmailRepo
) : ViewModel() {

    private val _postResult = MutableStateFlow<PostResult<Unit>?>(null)
    val postResult: StateFlow<PostResult<Unit>?> = _postResult

    private val _isResendLoading = MutableStateFlow<Boolean>(false)
    val isResendLoading: StateFlow<Boolean> = _isResendLoading

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _postResult.value = repo.sendPasswordResetEmail(email)
        }
    }

    fun resendVerification(email: String) {
        viewModelScope.launch {
            _isResendLoading.value = true
            _postResult.value = repo.sendPasswordResetEmail(email)
            _isResendLoading.value = false
        }
    }
}