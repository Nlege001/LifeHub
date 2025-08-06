package com.example.lifehub.features.moodhabits.data

import com.example.core.utils.toLocalDate
import com.example.lifehub.features.dashboard.home.data.Mood
import com.example.lifehub.network.data.MoodEntry
import java.time.LocalDate

data class DayMood(
    val date: LocalDate,
    val mood: Mood?,
    val intensity: Float,
) {
    companion object {
        fun generateDayMoods(
            entries: List<MoodEntry>,
            days: List<LocalDate>
        ): List<DayMood> {
            return days.map { date ->
                val dayEntries = entries.filter { it.timestamp.toLocalDate() == date }

                val dominantEntry = dayEntries.maxByOrNull { it.intensity }

                DayMood(
                    date = date,
                    mood = dominantEntry?.mood,
                    intensity = dominantEntry?.intensity ?: 0f
                )
            }
        }
    }

}