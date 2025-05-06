package com.example.lifehub.features.dashboard.home.data

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.wpinterviewpractice.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Mood(
    val emoji: String,
    @StringRes val label: Int,
) : Parcelable {
    HAPPY(
        emoji = "😀",
        label = R.string.happy
    ),
    NEUTRAL(
        emoji = "😐",
        label = R.string.neutral
    ),
    SAD(
        emoji = "☹️",
        label = R.string.sad
    ),
    ANGRY(
        emoji = "😠",
        label = R.string.angry
    ),
    ANXIOUS(
        emoji = "😰",
        label = R.string.anxious
    ),
    TIRED(
        emoji = "🥱",
        label = R.string.tired
    ),

}