package com.example.lifehub.features.dashboard.home.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.core.values.Colors
import com.example.wpinterviewpractice.R

enum class Mood(
    val emoji: String,
    @StringRes val label: Int,
    val color: Color,
) {
    HAPPY(
        emoji = "😀",
        label = R.string.happy,
        color = Colors.Happy,
    ),
    NEUTRAL(
        emoji = "😐",
        label = R.string.neutral,
        color = Colors.Neutral,
    ),
    SAD(
        emoji = "☹️",
        label = R.string.sad,
        color = Colors.Sad,
    ),
    ANGRY(
        emoji = "😠",
        label = R.string.angry,
        color = Colors.Angry,
    ),
    ANXIOUS(
        emoji = "😰",
        label = R.string.anxious,
        color = Colors.Anxious,
    ),
    TIRED(
        emoji = "🥱",
        label = R.string.tired,
        color = Colors.Tired,
    ),

}