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
class ResetEmailRepo @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) {
    suspend fun sendPasswordResetEmail(
        email: String
    ): PostResult<Unit> {
        return try {
            firebaseAuthService.sendPasswordResetEmail(
                email = email,
                actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setUrl(Page.PASSWORD_RESET_SUCCESS.deeplinkRoute)
                    .setHandleCodeInApp(true)
                    .setAndroidPackageName(
                        Constants.PACKAGE_NAME,
                        true,
                        null
                    )
                    .build()
            ).await()

            PostResult.Success(Unit)
        } catch (e: Exception) {
            PostResult.Error("ResetEmailRepo: Failed with error $e")
        }
    }
}