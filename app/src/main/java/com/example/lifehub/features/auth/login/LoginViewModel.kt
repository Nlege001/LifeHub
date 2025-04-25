package com.example.lifehub.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.PostResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepo
) : ViewModel() {

    private val _postResult = MutableStateFlow<PostResult<Unit>?>(null)
    val postResult: StateFlow<PostResult<Unit>?> = _postResult

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signIn(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _postResult.value = repo.signIn(email, password)
            _isLoading.value = false
        }

    }
}