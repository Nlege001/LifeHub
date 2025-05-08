package com.example.lifehub.features.profile.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.wpinterviewpractice.R

enum class ProfileActionItems(
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
    ),
    CHANGE_EMAIL(
        icon = R.drawable.ic_change_email,
        text = R.string.change_email
    ),
    SET_PIN(
        icon = R.drawable.ic_pin,
        text = R.string.set_pin
    ),
    CHANGE_PIN(
        icon = R.drawable.ic_change_pin,
        text = R.string.change_pin
    ),
    DISABLE_PIN(
        icon = R.drawable.ic_disable_pin,
        text = R.string.disable_pin
    ),
    SIGN_OUT(
        icon = R.drawable.ic_sign_out,
        text = R.string.sign_out
    ),
    DELETE_ACCOUNT(
        icon = R.drawable.ic_delete,
        text = R.string.delete_account
    )
}