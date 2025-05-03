package com.example.lifehub.features.dashboard.sidemenu

import com.example.core.data.ViewState
import com.example.core.room.user.UserDao
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SideMenuRepo @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao
) {
    suspend fun getSideMenuData(): ViewState<SideMenuData> {
        val currentUser = firebaseAuth.currentUser ?: return ViewState.Error()
        val userId = currentUser.uid

        val profilePictureUrl = userDao.getProfilePicture(userId)
        val profileBackGroundPictureUrl = userDao.getProfileBackground(userId)

        return ViewState.Content(
            SideMenuData(
                profilePicture = profileBackGroundPictureUrl,
                backGroundPicture = profilePictureUrl,
                items = SideMenuItem.getSideMenuItems()
            )
        )
    }
}