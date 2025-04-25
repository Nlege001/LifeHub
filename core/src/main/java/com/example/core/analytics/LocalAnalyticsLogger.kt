package com.example.core.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsLogger = staticCompositionLocalOf<AnalyticsLogger> {
    error("No AnalyticsLogger provided")
}