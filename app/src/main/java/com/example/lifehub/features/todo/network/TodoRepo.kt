package com.example.lifehub.features.todo.network

import com.example.core.data.PostResult
import com.example.core.data.ViewState
import com.example.lifehub.features.todo.data.TodoData
import javax.inject.Inject

class TodoRepo @Inject constructor(
    private val service: TodoService,
) {
    suspend fun saveTodos(
        data: TodoData
    ): PostResult<Unit> {
        val result = service.saveTodos(data)
        return result?.let {
            PostResult.Success(it)
        } ?: PostResult.Error()
    }

    suspend fun getTodos(): ViewState<List<TodoData>> {
        return service.getTodos()
    }

    suspend fun getTodoById(todoId: String?): ViewState<TodoData> {
        return if (todoId.isNullOrEmpty()) {
            ViewState.Content(
                TodoData(
                    date = null,
                    items = emptyList()
                )
            )
        } else {
            service.getTodoById(todoId)
        }
    }
}