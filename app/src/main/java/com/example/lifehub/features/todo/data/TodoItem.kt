package com.example.lifehub.features.todo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    var isComplete: Boolean = false,
) : Parcelable