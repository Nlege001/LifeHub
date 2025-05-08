package com.example.lifehub.features.dashboard

import androidx.lifecycle.ViewModel
import com.example.lifehub.network.auth.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    fun getUserId(): String? {
        return authService.currentUserId()
    }

}