package com.example.core.analytics

import androidx.navigation.NavBackStackEntry
import com.example.core.analytics.NavArgumentType.Companion.asPlaceholder

enum class Page(
    val label: String,
    val route: String,
    val arguments: Map<NavArgumentType, String> = emptyMap(),
    val deeplinkRoute: String = "",
    val hasBottomNav: Boolean = false,
    val hasNavDrawer: Boolean = false
) {
    // AUTH
    LOGIN(
        label = "Log In",
        route = "Login"
    ),
    SET_PIN(
        label = "Set Pin",
        route = "SetPin"
    ),
    CONFIRM_PIN(
        label = "Confirm Pin",
        route = "ConfirmPin/{${NavArgumentType.PIN.label}}",
        arguments = mapOf(NavArgumentType.PIN to NavArgumentType.PIN.label)
    ),
    VERIFY_PIN(
        label = "Verify Pin",
        route = "VerifyPin"
    ),
    DISABLE_PIN(
        label = "Disable Pin",
        route = "DisablePin"
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
    ),

    // Questionarie
    FIRST_NAME_LAST_NAME(
        label = "First Last Name Screen",
        route = "FirstLastNameScreen",
    ),
    DOB(
        label = "DaTe of birth",
        route = "DateOfBirth/{${NavArgumentType.FIRST_NAME.label}}/{${NavArgumentType.LAST_NAME.label}}",
        arguments = mapOf(
            NavArgumentType.FIRST_NAME to NavArgumentType.FIRST_NAME.label,
            NavArgumentType.LAST_NAME to NavArgumentType.LAST_NAME.label,
        )
    ),
    ACCOUNT_CREATION_SUCCESS(
        label = "Account Creation Success",
        route = "AccountCreationSuccess",
    ),

    // BOTTOM_NAV
    DASHBOARD_HOME(
        label = "Dashboard",
        route = "Dashboard",
        hasBottomNav = true,
        hasNavDrawer = true,
    ),
    POSTS(
        label = "Posts",
        route = "Posts",
        hasBottomNav = true,
        hasNavDrawer = true,
    ),
    PROGRESS(
        label = "Progress",
        route = "Progress",
        hasBottomNav = true,
        hasNavDrawer = true,
    ),
    MESSAGES(
        label = "Messages",
        route = "Messages",
        hasBottomNav = true,
        hasNavDrawer = true,
    ),
    PROFILE(
        label = "Profile",
        route = "Profile",
        hasBottomNav = true,
        hasNavDrawer = true,
    ),

    // SIDE MENU
    SIDE_MENU(
        label = "Side Menu",
        route = "Side Menu",
    ),
    JOURNAL(
        label = "Journal",
        route = "Journal",
    ),
    REFLECTIONS(
        label = "Reflections",
        route = "Reflections",
    ),
    INSIGHTS(
        label = "Insights",
        route = "Insights",
    ),
    CALENDAR(
        label = "Calendar",
        route = "Calendar",
    ),
    INVITE_A_FRIEND(
        label = "Invite A Friend",
        route = "Invite A Friend",
    ),
    SETTINGS(
        label = "SETTINGS",
        route = "Settings",
    ),
    SIGN_OUT(
        label = "Sign Out",
        route = "Sign Out",
    ),
    ARTICLE(
        label = "Article",
        route = "Article/${NavArgumentType.URL.asPlaceholder()}",
        arguments = mapOf(NavArgumentType.URL to NavArgumentType.URL.label)
    ),
    ARTICLE_LIST(
        label = "Article List",
        route = "Article List",
    ),
    TODO(
        label = "Todo",
        route = "Todo",
    ),
    TODO_LIST(
        label = "Todo list",
        route = "Todo list",
    );

    fun buildRoute(vararg args: String?): String {
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

    companion object {
        fun getCurrentPage(route: String?): Page? {
            return Page.entries.find { it.route == route }
        }
    }
}