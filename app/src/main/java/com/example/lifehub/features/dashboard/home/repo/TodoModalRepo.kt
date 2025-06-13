package com.example.lifehub.features.dashboard.home.repo

import com.example.core.data.ViewState
import com.example.lifehub.features.todo.data.TodoData
import com.example.lifehub.features.todo.network.TodoService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TodoModalRepo @Inject constructor(
    private val todoService: TodoService
) {
    suspend fun fetchMostRecentItem(): ViewState<List<TodoData>> {
        return when (val result = todoService.getTodos()) {
            is ViewState.Content -> {
                val data = result.data.take(1)
                ViewState.Content(data)
            }

            is ViewState.Error -> ViewState.Error()
            ViewState.Loading -> ViewState.Loading
        }
    }
}