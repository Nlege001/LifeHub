package com.example.lifehub.features.signup.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.PostResult
import com.example.lifehub.features.signup.repo.EmailVerificationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val repo: EmailVerificationRepo
) : ViewModel() {

    private val _postResult = MutableStateFlow<PostResult<Unit>?>(null)
    val postResult: StateFlow<PostResult<Unit>?> = _postResult

    private val _isEmailVerified = MutableStateFlow<PostResult<Boolean>?>(null)
    val isEmailVerified: StateFlow<PostResult<Boolean>?> = _isEmailVerified

    private val _isResendLoading = MutableStateFlow<Boolean>(false)
    val isResendLoading: StateFlow<Boolean> = _isResendLoading

    init {
        sendEmailVerification()
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun sendEmailVerification() {
        viewModelScope.launch {
            _postResult.value = repo.sendEmailVerification()
        }
    }

    fun resendVerification() {
        viewModelScope.launch {
            _isResendLoading.value = true
            _postResult.value = repo.sendEmailVerification()
            _isResendLoading.value = false
        }
    }

    fun isEmailVerified() {
        viewModelScope.launch {
            _isEmailVerified.value = repo.isEmailVerified()
        }
    }
}