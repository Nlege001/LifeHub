package com.example.lifehub.features.nav.navbuilder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.analytics.Page
import com.example.lifehub.features.todo.TodoListScreen
import com.example.lifehub.features.todo.TodosScreen

fun NavGraphBuilder.progressNavBuilder(
    navHostController: NavHostController,
) {
    composable(Page.TODO_LIST.route) {
        TodosScreen(
            onTodoClick = {},
            addTodo = {
                navHostController.navigate(Page.TODO.route)
            }
        )
    }
    composable(Page.TODO.route) {
        TodoListScreen(
            navBack = { navHostController.popBackStack() }
        )
    }
}