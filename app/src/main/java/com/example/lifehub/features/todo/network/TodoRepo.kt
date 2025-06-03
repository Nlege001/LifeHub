package com.example.lifehub.features.todo.network

import com.example.core.data.PostResult
import com.example.core.data.ViewState
import com.example.lifehub.features.todo.data.TodoData
import com.example.lifehub.features.todo.data.TodoItem
import javax.inject.Inject

class TodoRepo @Inject constructor(
    private val service: TodoService,
) {
    suspend fun saveTodos(
        items: List<TodoItem>,
        date: Long
    ): PostResult<Unit> {
        val result = service.saveTodos(items, date)
        return result?.let {
            PostResult.Success(it)
        } ?: PostResult.Error()
    }

    suspend fun getTodos(): ViewState<List<TodoData>> {
        return service.getTodos()
    }
}