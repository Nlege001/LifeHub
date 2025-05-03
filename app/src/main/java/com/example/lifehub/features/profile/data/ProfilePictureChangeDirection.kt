package com.example.lifehub.features.profile.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ProfilePictureChangeDirection : Parcelable {
    CHANGE_PROFILE_PICTURE,
    CHANGE_BACKGROUND_PICTURE
}