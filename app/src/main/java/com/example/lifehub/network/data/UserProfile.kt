package com.example.lifehub.network.data

data class UserProfile(
    val userId: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val dob: Long = 0L
)