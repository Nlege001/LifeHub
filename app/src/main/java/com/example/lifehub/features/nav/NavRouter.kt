package com.example.lifehub.features.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavRouter {
    abstract fun startFlow(
        navController: NavHostController,
    )

    abstract fun flow(
        navController: NavHostController,
        onFlowComplete: () -> Unit,
        builder: NavGraphBuilder
    )
}