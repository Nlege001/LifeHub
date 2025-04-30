package com.example.core.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class UserEntity(
    @PrimaryKey val userId: String,
    val hasCompletedQuestionaire: Boolean = false,
)