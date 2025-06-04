package com.example.lifehub.features.nav.navbuilder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.analytics.NavArgumentType
import com.example.core.analytics.Page
import com.example.lifehub.features.todo.TodoListScreen
import com.example.lifehub.features.todo.TodosScreen

fun NavGraphBuilder.progressNavBuilder(
    navHostController: NavHostController,
) {
    composable(Page.TODO_LIST.route) {
        TodosScreen(
            onTodoClick = {
                navHostController.navigate(Page.TODO.buildRoute(it))
            },
            addTodo = {
                navHostController.navigate(Page.TODO.buildRoute(null))
            }
        )
    }
    composable(
        route = Page.TODO.route,
        arguments = listOf(
            navArgument(NavArgumentType.ID.label) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        TodoListScreen(
            navBack = { navHostController.popBackStack() }
        )
    }
}