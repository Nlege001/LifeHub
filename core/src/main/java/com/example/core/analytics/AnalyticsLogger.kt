package com.example.core.analytics

interface AnalyticsLogger {
    fun logScreenSeen(screen: String)
    fun logCtaClicked(label: String, screen: Page)
    fun logScreenResult(screen: String, result: String)
}