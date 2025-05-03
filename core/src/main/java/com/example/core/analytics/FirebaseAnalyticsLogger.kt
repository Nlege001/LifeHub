package com.example.core.analytics

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class FirebaseAnalyticsLogger : AnalyticsLogger {

    private val analytics = Firebase.analytics

    override fun logScreenSeen(screen: String) {
        Log.d("Analytics", "Screen: $screen")
        analytics.logEvent("screen_seen", bundleOf("screen" to screen))
    }

    override fun logCtaClicked(label: String, page: Page) {
        val params = bundleOf("label" to label)
        params.putString("screen", page.label)
        analytics.logEvent("cta_clicked", params)
    }

    override fun logScreenResult(screen: String, result: String) {
        analytics.logEvent("screen_result", bundleOf("screen" to screen, "result" to result))
    }

    private fun bundleOf(vararg pairs: Pair<String, Any?>): Bundle =
        Bundle().apply {
            for ((key, value) in pairs) {
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Boolean -> putBoolean(key, value)
                    is Double -> putDouble(key, value)
                    is Long -> putLong(key, value)
                }
            }
        }
}