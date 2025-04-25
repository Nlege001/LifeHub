package com.example.lifehub.features.auth.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.analytics.NavFlows
import com.example.core.analytics.Page
import com.example.lifehub.features.auth.login.LogInScreen
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
                }
            )
        }
        composable(Page.SIGN_UP.route) {
            SignUpScreen(
                done = { navController.navigate(Page.EMAIL_VERIFICATION.route) }
            )
        }
        composable(Page.EMAIL_VERIFICATION.route) {
            EmailVerificationScreen(
                onSuccess = { navController.navigate(Page.SIGN_UP_SUCCESS.route) }
            )
        }
        composable(Page.SIGN_UP_SUCCESS.route) {
            SignUpSuccessScreen(
                getStarted = {}
            )
        }
    }
}