package com.example.lifehub.features.dashboard.home.data

import com.example.lifehub.network.data.MoodEntry

data class MoodData(
    val entries: List<MoodEntry>,
    val streak: MoodStreak?
)