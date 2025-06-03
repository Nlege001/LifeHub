package com.example.lifehub.features.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.PostResult
import com.example.lifehub.features.todo.data.TodoItem
import com.example.lifehub.features.todo.network.TodoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepo: TodoRepo
) : ViewModel() {

    private val _items = MutableStateFlow(listOf(TodoItem()))
    val items: StateFlow<List<TodoItem>> = _items

    private val _date = MutableStateFlow<Long?>(null)
    val date: StateFlow<Long?> = _date

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _postResult = MutableStateFlow<PostResult<Unit>?>(null)
    val postResult: StateFlow<PostResult<Unit>?> = _postResult

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

    fun updateDate(date: Long) {
        _date.update { date }
    }

    fun validateSave(): Boolean {
        return _items.value.all { it.text.isNotEmpty() } && date.value != null
    }

    fun save() {
        viewModelScope.launch {
            Log.d("Naol", "trying to save")
            _isLoading.value = true
            _date.value?.let {
                _postResult.value = todoRepo.saveTodos(_items.value, it)
            }
            _isLoading.value = false
        }
    }
}