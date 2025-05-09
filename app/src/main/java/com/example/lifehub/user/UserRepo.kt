package com.example.lifehub.user

import com.example.core.room.user.UserDao
import com.example.core.room.user.UserEntity
import com.example.lifehub.network.auth.FirebaseAuthService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UserRepo @Inject constructor(
    private val userDao: UserDao,
    private val firebaseAuthService: FirebaseAuthService
) {

    suspend fun getUser(): UserEntity? {
        val userId = firebaseAuthService.currentUserId() ?: return null
        return userDao.getUser(userId)
    }

    suspend fun saveUser(completed: Boolean) {
        val uid = firebaseAuthService.currentUserId() ?: return
        val user = UserEntity(
            userId = uid,
            hasCompletedQuestionaire = completed
        )
        userDao.saveUser(user)
    }

    suspend fun updateQuestionnaireStatus(completed: Boolean): Unit? {
        val userId = firebaseAuthService.currentUserId() ?: return null
        return userDao.updateQuestionnaireStatus(userId, completed)
    }

}