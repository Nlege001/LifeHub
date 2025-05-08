package com.example.lifehub.features.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.core.analytics.Page

interface NavRouter {
    abstract fun startFlow(
        navController: NavHostController,
    )

    abstract fun flow(
        navController: NavHostController,
        builder: NavGraphBuilder,
        startDestination: Page
    )
}