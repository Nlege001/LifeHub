package com.example.lifehub.features.dashboard.home.appbar

data class AppBarIcon(
    val iconResId: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)