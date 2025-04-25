package com.example.core.analytics

enum class Page(
    val label: String,
    val route: String
) {
    // AUTH
    LOGIN(
        label = "Log In",
        route = "Login"
    ),
    SIGN_UP(
        label = "Sign Up",
        route = "SignUp"
    ),
    SIGN_UP_SUCCESS(
        label = "Sign Up Success",
        route = "SignUpSuccess"
    ),
    EMAIL_VERIFICATION(
        label = "Email Verification",
        route = "EmailVerification"
    )
}