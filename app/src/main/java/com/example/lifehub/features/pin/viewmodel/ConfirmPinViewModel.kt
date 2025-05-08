package com.example.lifehub.features.pin.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.core.analytics.NavArgumentType
import com.example.core.data.PostResult
import com.example.lifehub.encryptedsharedpreferences.SecurePreferences
import com.example.lifehub.network.auth.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ConfirmPinViewModel @Inject constructor(
    private val securePreferences: SecurePreferences,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val pin : String = checkNotNull(savedStateHandle[NavArgumentType.PIN.label])

    private val _setPin = MutableStateFlow<PostResult<Unit>?>(null)
    val setPin: StateFlow<PostResult<Unit>?> = _setPin

    fun setPin(pin: String) {
        val currentUserId = authService.currentUserId()
        if (currentUserId == null) {
            _setPin.value = PostResult.Error()
        }
        currentUserId?.let {
            securePreferences.saveUserPin(it, pin)
            _setPin.value = PostResult.Success(Unit)
        }
    }
}