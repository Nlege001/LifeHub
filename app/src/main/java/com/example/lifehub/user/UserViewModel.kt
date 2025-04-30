package com.example.lifehub.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.room.user.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model that gets user data that has been saved to room
 **/
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepo
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            _user.value = repo.getUser()
        }
    }

    fun saveUser(completed: Boolean) {
        viewModelScope.launch {
            repo.saveUser(completed)

        }
    }

    fun updateQuestionnaireStatus(
        completed: Boolean
    ) {
        viewModelScope.launch {
            repo.updateQuestionnaireStatus(completed)
        }
    }
}