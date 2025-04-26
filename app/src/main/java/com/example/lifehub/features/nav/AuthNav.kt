package com.example.lifehub.features.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.core.analytics.NavArgumentType
import com.example.core.analytics.NavFlows
import com.example.core.analytics.Page
import com.example.lifehub.features.auth.login.LogInScreen
import com.example.lifehub.features.auth.login.PasswordResetSuccessScreen
import com.example.lifehub.features.auth.login.ResetPasswordScreen
import com.example.lifehub.features.auth.signup.composables.EmailVerificationScreen
import com.example.lifehub.features.auth.signup.composables.SignUpScreen
import com.example.lifehub.features.auth.signup.composables.SignUpSuccessScreen

fun NavGraphBuilder.auth(navController: NavHostController) {
    navigation(
        startDestination = Page.LOGIN.route,
        route = NavFlows.AUTH.route,
    ) {
        composable(Page.LOGIN.route) {
            LogInScreen(
                onSignInSuccessful = {},
                navToSignUp = {
                    navController.navigate(Page.SIGN_UP.route)
                },
                forgotPassword = {
                    navController.navigate(Page.PASSWORD_RESET.buildRoute(it))
                }
            )
        }
        composable(Page.SIGN_UP.route) {
            SignUpScreen(
                done = { navController.navigate(Page.EMAIL_VERIFICATION.route) }
            )
        }
        composable(Page.EMAIL_VERIFICATION.route) {
            EmailVerificationScreen()
        }
        composable(
            route = Page.SIGN_UP_SUCCESS.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = Page.SIGN_UP_SUCCESS.deeplinkRoute
                }
            )
        ) {
            SignUpSuccessScreen(
                getStarted = {}
            )
        }
        composable(
            route = Page.PASSWORD_RESET_SUCCESS.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = Page.PASSWORD_RESET_SUCCESS.deeplinkRoute
                }
            )
        ) {
            PasswordResetSuccessScreen(
                backToLogIn = { navController.navigate(Page.LOGIN.route) }
            )
        }
        composable(
            route = Page.PASSWORD_RESET.route,
            arguments = listOf(
                navArgument(NavArgumentType.EMAIL.label) { type = NavType.StringType }
            )
        ) { entry ->
            val email = Page.PASSWORD_RESET.getArgument(NavArgumentType.EMAIL, entry) ?: ""
            ResetPasswordScreen(email = email)
        }
    }
}