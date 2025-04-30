package com.example.lifehub.features.nav.navrouters

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.core.analytics.NavFlows
import com.example.core.analytics.Page
import com.example.lifehub.features.auth.login.LogInScreen
import com.example.lifehub.features.auth.login.PasswordResetSuccessScreen
import com.example.lifehub.features.auth.login.ResetPasswordScreen
import com.example.lifehub.features.auth.signup.composables.EmailVerificationScreen
import com.example.lifehub.features.auth.signup.composables.SignUpScreen
import com.example.lifehub.features.auth.signup.composables.SignUpSuccessScreen
import com.example.lifehub.features.nav.NavRouter
import javax.inject.Inject

/**
 * Nav router for the authentication flow of the app
 **/
class AuthNav @Inject constructor(
    private val questionarieNav: QuestionarieNav,
    private val mainNav: MainNav
) : NavRouter {

    override fun startFlow(
        navController: NavHostController,
    ) {
        navController.navigate(NavFlows.AUTH.route)
    }

    override fun flow(
        navController: NavHostController,
        builder: NavGraphBuilder
    ) {
        builder.navigation(
            startDestination = Page.LOGIN.route,
            route = NavFlows.AUTH.route,
        ) {
            composable(Page.LOGIN.route) {
                LogInScreen(
                    onSignInSuccessful = { isQuestionaireComplete ->
                        if (isQuestionaireComplete) {
                            mainNav.startFlow(navController)
                        } else {
                            questionarieNav.startFlow(navController)
                        }
                    },
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
                    getStarted = {
                        questionarieNav.startFlow(navController)
                    }
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
            ) { entry ->
                ResetPasswordScreen()
            }
        }
    }
}