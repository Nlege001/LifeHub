package com.example.core.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsLogger = staticCompositionLocalOf<AnalyticsLogger> {
    object : AnalyticsLogger {
        override fun logScreenSeen(screen: String) {}
        override fun logCtaClicked(label: String, screen: Page) {}
        override fun logScreenResult(screen: String, result: String) {}
    }
}