package com.example.lifehub.features.signup

import com.example.core.data.PostResult
import com.example.lifehub.network.auth.FirebaseAuthService
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ViewModelScoped
class SignUpRepo @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) {
    suspend fun signUp(
        email: String,
        password: String
    ): PostResult<Unit> {
        return try {
            firebaseAuthService
                .signUp(email, password)
                .await()

            PostResult.Success(Unit)
        } catch (e: Exception) {
            PostResult.Error("LoginRepo failed with $e")
        }
    }
}