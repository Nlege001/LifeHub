package com.example.lifehub.features.todo.data

import java.util.UUID

data class TodoData(
    val id: String = UUID.randomUUID().toString(),
    val items: List<TodoItem>,
    val date: Long?
)