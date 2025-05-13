package com.example.lifehub.features.todo

import androidx.lifecycle.ViewModel
import com.example.lifehub.features.todo.data.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {

    private val _items = MutableStateFlow(listOf(TodoItem()))
    val items: StateFlow<List<TodoItem>> = _items

    fun updateText(id: String, newText: String) {
        _items.update { list ->
            list.map { if (it.id == id) it.copy(text = newText) else it }
        }
    }

    fun updateChecked(id: String, isChecked: Boolean) {
        _items.update { list ->
            list.map { if (it.id == id) it.copy(isComplete = isChecked) else it }
        }
    }

    fun move(from: Int, to: Int) {
        val list = _items.value.toMutableList()
        val item = list.removeAt(from)
        list.add(to, item)
        _items.value = list
    }

    fun addItem() {
        _items.update { it + TodoItem() }
    }

    fun deleteItem(id: String) {
        _items.update { list ->
            list.filterNot { it.id == id }
        }
    }
}