package com.example.lifehub.features.auth.signup.repo

import com.example.core.analytics.Page
import com.example.core.data.PostResult
import com.example.core.utils.Constants
import com.example.lifehub.network.auth.FirebaseAuthService
import com.google.firebase.auth.ActionCodeSettings
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ViewModelScoped
class EmailVerificationRepo @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) {
    suspend fun sendEmailVerification(): PostResult<Unit> {
        return try {
            firebaseAuthService
                .emailVerification(
                    actionCodeSettings = ActionCodeSettings.newBuilder()
                        .setUrl(Page.SIGN_UP_SUCCESS.deeplinkRoute)
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                            Constants.PACKAGE_NAME,
                            true,
                            null
                        )
                        .build()
                )
                .await()

            PostResult.Success(Unit)
        } catch (e: Exception) {
            PostResult.Error("EmailVerificationRepo failed to send verification email $e")
        }
    }

    suspend fun isEmailVerified(): PostResult<Boolean> {
        return try {
            val user =
                firebaseAuthService.getCurrentUser() ?: return PostResult.Error("No user signed in")
            user.reload().await()

            PostResult.Success(user.isEmailVerified)
        } catch (e: Exception) {
            PostResult.Error("Failed to check email verification status: $e")
        }
    }
}