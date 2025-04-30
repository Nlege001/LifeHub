package com.example.lifehub.user

import com.example.core.room.user.UserDao
import com.example.core.room.user.UserEntity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UserRepo @Inject constructor(
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid

    suspend fun getUser(): UserEntity? {
        val userId = getCurrentUserId() ?: return null
        return userDao.getUser(userId)
    }

    suspend fun saveUser(completed: Boolean) {
        val uid = getCurrentUserId() ?: return
        val user = UserEntity(
            userId = uid,
            hasCompletedQuestionaire = completed
        )
        userDao.saveUser(user)
    }

    suspend fun updateQuestionnaireStatus(completed: Boolean): Unit? {
        val userId = getCurrentUserId() ?: return null
        return userDao.updateQuestionnaireStatus(userId, completed)
    }

}