package com.example.lifehub.features.questionaire.repo

import com.example.core.data.PostResult
import com.example.lifehub.network.data.UserProfile
import com.example.lifehub.network.user.UserService
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class UserRepo @Inject constructor(
    private val userService: UserService,
    private val auth: FirebaseAuth
) {
    suspend fun saveUserProfile(firstName: String, lastName: String?, dob: Long): PostResult<Unit> {
        val currentUser = auth.currentUser ?: return PostResult.Error("No user signed in")

        val userProfile = UserProfile(
            userId = currentUser.uid,
            firstName = firstName,
            lastName = lastName,
            dob = dob
        )

        return try {
            userService.saveUserProfile(userProfile)
            PostResult.Success(Unit)
        } catch (e: Exception) {
            PostResult.Error("$e")
        }
    }

    fun getUserProfile(): Flow<UserProfile?> = flow {
        val user = auth.currentUser
        if (user == null) {
            emit(null)
            return@flow
        }

        try {
            emit(userService.getUserProfile(user.uid))
        } catch (e: Exception) {
            emit(null)
        }
    }
}