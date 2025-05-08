package com.example.lifehub.features.profile.data

import android.os.Parcelable
import com.example.wpinterviewpractice.R
import kotlinx.parcelize.Parcelize

sealed class ProfileBottomSheetItem : Parcelable {

    abstract val title: Int
    abstract fun getItems(
        hasPin: Boolean
    ): List<ProfileActionItems>

    @Parcelize
    data class ProfilePicture(
        override val title: Int = R.string.profile_picture_change,
    ) : ProfileBottomSheetItem() {
        override fun getItems(hasPin: Boolean): List<ProfileActionItems> = listOf(
            ProfileActionItems.TAKE_PICTURE,
            ProfileActionItems.OPEN_GALLERY
        )
    }

    @Parcelize
    data class AccountOptions(
        override val title: Int = R.string.account_options,
    ) : ProfileBottomSheetItem() {
        override fun getItems(hasPin: Boolean): List<ProfileActionItems> {
            val pinItems = if (hasPin) {
                listOf(
                    ProfileActionItems.CHANGE_PIN,
                    ProfileActionItems.DISABLE_PIN
                )
            } else {
                listOf(ProfileActionItems.SET_PIN)
            }

            return pinItems + listOf(
                ProfileActionItems.CHANGE_EMAIL,
                ProfileActionItems.SIGN_OUT,
                ProfileActionItems.DELETE_ACCOUNT,
            )
        }
    }
}