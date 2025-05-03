package com.example.lifehub.features.profile

import com.example.core.data.ViewState
import com.example.core.room.images.ProfileImageEntity
import com.example.core.room.user.UserDao
import com.example.core.utils.formatFirebaseTimestamp
import com.example.lifehub.features.profile.data.ProfileData
import com.example.lifehub.network.user.UserService
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ProfileRepo @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userService: UserService,
    private val userDao: UserDao
) {
    suspend fun getProfileData(): ViewState<ProfileData> {
        val currentUser = firebaseAuth.currentUser ?: return ViewState.Error()
        val userId = currentUser.uid

        val profile = userService.getUserProfile(userId) ?: return ViewState.Error()
        val profilePictureUrl = userDao.getProfilePicture(userId)
        val profileBackGroundPictureUrl = userDao.getProfileBackground(userId)

        return ViewState.Content(
            ProfileData(
                profileBackGroundPictureUrl = profilePictureUrl,
                profilePictureUrl = profileBackGroundPictureUrl,
                firstName = profile.firstName,
                lastName = profile.lastName,
                email = currentUser.email ?: return ViewState.Error(),
                dob = profile.dob,
                memberSince = profile.joinedAt.formatFirebaseTimestamp(),
                userId = userId
            )
        )
    }

    suspend fun saveProfilePicture(userId: String, background: ByteArray) {
        ensureProfileImageRowExists(userId)
        userDao.updateProfilePicture(userId, background)
    }

    suspend fun saveProfileBackground(userId: String, background: ByteArray) {
        ensureProfileImageRowExists(userId)
        userDao.updateProfileBackground(userId, background)
    }


    private suspend fun ensureProfileImageRowExists(userId: String) {
        val pictureExists = userDao.getProfilePicture(userId) != null
        val backgroundExists = userDao.getProfileBackground(userId) != null

        if (!pictureExists && !backgroundExists) {
            userDao.saveProfileImages(
                ProfileImageEntity(
                    userId = userId,
                    profilePicture = null,
                    profileBackground = null
                )
            )
        }
    }
}