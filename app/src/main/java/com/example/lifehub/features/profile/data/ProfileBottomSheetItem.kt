package com.example.lifehub.features.profile.data

import android.os.Parcelable
import com.example.wpinterviewpractice.R
import kotlinx.parcelize.Parcelize

sealed class ProfileBottomSheetItem : Parcelable {

    abstract val title: Int
    abstract val items: List<ProfileActionItems>

    @Parcelize
    data class ProfilePicture(
        override val title: Int = R.string.profile_picture_change,
        override val items: List<ProfileActionItems> = listOf(
            ProfileActionItems.TAKE_PICTURE,
            ProfileActionItems.OPEN_GALLERY
        )
    ) : ProfileBottomSheetItem()

    @Parcelize
    data class AccountOptions(
        override val title: Int = R.string.account_options,
        override val items: List<ProfileActionItems> = listOf(
            ProfileActionItems.CHANGE_EMAIL,
            ProfileActionItems.SIGN_OUT,
            ProfileActionItems.DELETE_ACCOUNT,
        )
    ) : ProfileBottomSheetItem()
}