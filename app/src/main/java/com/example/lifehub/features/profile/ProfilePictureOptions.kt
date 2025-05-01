package com.example.lifehub.features.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.wpinterviewpractice.R

enum class ProfilePictureOptions(
    @DrawableRes val icon: Int,
    @StringRes val text: Int
) {
    OPEN_GALLERY(
        icon = R.drawable.ic_open_gallery,
        text = R.string.open_gallery
    ),
    TAKE_PICTURE(
        icon = R.drawable.ic_take_photo,
        text = R.string.take_a_photo
    )
}