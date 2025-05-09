package com.example.lifehub.features.dashboard.home.data

data class MoodStreak(
    val lastEntryEpochDay: Long = 0L,
    val currentStreak: Int = 0,
    val shouldShowModal: Boolean = false
)