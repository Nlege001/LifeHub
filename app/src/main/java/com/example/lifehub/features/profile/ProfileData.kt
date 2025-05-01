package com.example.lifehub.features.profile

data class ProfileData(
    val profileBackGroundPictureUrl: String?,
    val profilePictureUrl: String?,
    val firstName: String,
    val lastName: String?,
    val email: String,
    val dob: Long,
    val memberSince: String
)