package com.example.core.analytics

import androidx.navigation.NavBackStackEntry

enum class Page(
    val label: String,
    val route: String,
    val arguments: Map<NavArgumentType, String> = emptyMap(),
    val deeplinkRoute: String = ""
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
        route = "SignUpSuccess",
        deeplinkRoute = "https://lifehub-c1851.firebaseapp.com/EmailVerificationSuccess"
    ),
    EMAIL_VERIFICATION(
        label = "Email Verification",
        route = "EmailVerification",
    ),
    PASSWORD_RESET(
        label = "Password Reset",
        route = "PasswordReset",
    ),
    PASSWORD_RESET_SUCCESS(
        label = "Password Reset Success",
        route = "PasswordResetSuccess",
        deeplinkRoute = "https://lifehub-c1851.firebaseapp.com/PasswordResetSuccess"
    );

    fun buildRoute(vararg args: String): String {
        var builtRoute = route
        arguments.values.forEachIndexed { index, key ->
            builtRoute = builtRoute.replace("{$key}", args.getOrNull(index) ?: "")
        }
        return builtRoute
    }

    fun getArgument(type: NavArgumentType, entry: NavBackStackEntry): String? {
        val key = arguments[type]
        return key?.let { entry.arguments?.getString(it) }
    }
}