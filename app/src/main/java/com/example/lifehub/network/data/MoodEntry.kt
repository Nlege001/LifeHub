package com.example.lifehub.network.data

import androidx.annotation.Keep
import com.example.lifehub.features.dashboard.home.data.Mood

@Keep
data class MoodEntry(
    val mood: Mood = Mood.entries.random(),
    val reflection: String? = null,
    val intensity: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)