package com.example.core.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun TrackScreenSeen(page: Page) {
    val analytics = LocalAnalyticsLogger.current
    LaunchedEffect(page) {
        analytics.logScreenSeen(page.label)
    }
}