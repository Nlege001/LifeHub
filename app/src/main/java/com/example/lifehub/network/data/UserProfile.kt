package com.example.lifehub.network.data

import com.google.firebase.Timestamp

data class UserProfile(
    val userId: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val dob: Long = 0L,
    val joinedAt: Timestamp = Timestamp.now()
)