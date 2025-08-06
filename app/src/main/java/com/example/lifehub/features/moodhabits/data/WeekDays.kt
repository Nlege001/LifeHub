package com.example.lifehub.features.moodhabits.data

import androidx.annotation.StringRes
import com.example.wpinterviewpractice.R

enum class WeekDays(@StringRes val label: Int) {
    MON(label = R.string.mon),
    TUE(label = R.string.tue),
    WED(label = R.string.wed),
    THU(label = R.string.thu),
    FRI(label = R.string.fri),
    SAT(label = R.string.sat),
    SUN(label = R.string.sun)
}