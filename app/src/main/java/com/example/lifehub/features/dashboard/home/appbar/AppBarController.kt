package com.example.lifehub.features.dashboard.home.appbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun AppBarController.SetButtons(
    list: List<AppBarIcon>
) {
    LaunchedEffect(Unit) {
        actions = list
    }

    DisposableEffect(Unit) {
        onDispose { clear() }
    }
}