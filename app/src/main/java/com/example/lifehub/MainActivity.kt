package com.example.lifehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.analytics.FirebaseAnalyticsLogger
import com.example.core.analytics.LocalAnalyticsLogger
import com.example.core.analytics.Page
import com.example.core.theme.LifeHubTheme
import com.example.lifehub.features.login.LogInScreen
import com.example.lifehub.features.signup.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeHubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    CompositionLocalProvider(
                        LocalAnalyticsLogger provides FirebaseAnalyticsLogger()
                    ) {
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = Page.LOGIN.route,
                            builder = {
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
                                        done = {}
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}