package com.example.core.analytics

enum class Page(
    val label: String,
    val route: String
) {
    // AUTH
    LOGIN(
        label = "Log in",
        route = "Login"
    ),
    SIGN_UP(
        label = "Sign up",
        route = "SignUp"
    )
}