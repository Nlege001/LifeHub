package com.example.lifehub

import android.app.Application
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LifeHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.analytics
    }
}