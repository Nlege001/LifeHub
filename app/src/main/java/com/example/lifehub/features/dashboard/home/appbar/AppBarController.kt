package com.example.lifehub.features.dashboard.home.appbar

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AppBarController {
    var actions: List<AppBarIcon> by mutableStateOf(emptyList())

    fun clear() {
        actions = emptyList()
    }
}

val LocalAppBarController = compositionLocalOf<AppBarController> {
    error("AppBarController not provided")
}