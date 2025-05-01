package com.example.lifehub.features.profile

import com.example.core.data.ViewState
import com.example.core.utils.formatFirebaseTimestamp
import com.example.lifehub.network.user.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ProfileRepo @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val userService: UserService
) {
    suspend fun getProfileData(): ViewState<ProfileData> {
        val currentUser = firebaseAuth.currentUser ?: return ViewState.Error()
        val profile = userService.getUserProfile(currentUser.uid) ?: return ViewState.Error()

        return ViewState.Content(
            ProfileData(
                profileBackGroundPictureUrl = null,
                profilePictureUrl = null,
                firstName = profile.firstName,
                lastName = profile.lastName,
                email = currentUser.email ?: return ViewState.Error(),
                dob = profile.dob,
                memberSince = profile.joinedAt.formatFirebaseTimestamp()
            )
        )
    }
}