package com.example.lifehub.features.pin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.data.PostResult
import com.example.lifehub.encryptedsharedpreferences.SecurePreferences
import com.example.lifehub.network.auth.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DisablePinViewModel @Inject constructor(
    private val securePreferences: SecurePreferences,
    private val authService: AuthService,
) : ViewModel() {

    private val _isError = MutableStateFlow<Boolean?>(null)
    val isError: StateFlow<Boolean?> = _isError

    private val _clearPin = MutableStateFlow<PostResult<Unit>?>(null)
    val clearPin: StateFlow<PostResult<Unit>?> = _clearPin

    fun verifyPin(pin: String) {
        val currentUserId = authService.currentUserId()
        if (currentUserId == null) {
            _isError.value = true
        }
        currentUserId?.let {
            val currentPin = securePreferences.getUserPin(it)
            _isError.value = pin != currentPin
        }
    }

    fun clearPin() {
        val currentUserId = authService.currentUserId()
        currentUserId?.let {
            securePreferences.clearUserPin(it)
            _clearPin.value = PostResult.Success(Unit)
        }?: run {
            _clearPin.value = PostResult.Error()
        }
    }
}