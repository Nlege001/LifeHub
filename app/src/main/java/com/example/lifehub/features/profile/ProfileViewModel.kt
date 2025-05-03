package com.example.lifehub.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ViewState
import com.example.lifehub.features.profile.data.ProfileData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepo
) : ViewModel() {

    private val _profile = MutableStateFlow<ViewState<ProfileData>>(ViewState.Loading)
    val profile: StateFlow<ViewState<ProfileData>> = _profile

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            _profile.value = ViewState.Loading
            _profile.value = repo.getProfileData()
        }
    }

    fun uploadProfilePicture(
        userId: String,
        imageBytes: ByteArray
    ) {
        viewModelScope.launch {
            repo.saveProfilePicture(userId, imageBytes)
            getProfile()
        }
    }

    fun uploadBackgroundPicture(
        userId: String,
        imageBytes: ByteArray
    ) {
        viewModelScope.launch {
            repo.saveProfileBackground(userId, imageBytes)
            getProfile()
        }
    }
}