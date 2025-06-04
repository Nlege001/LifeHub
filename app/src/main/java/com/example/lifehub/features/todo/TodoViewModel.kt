package com.example.lifehub.features.todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.analytics.NavArgumentType
import com.example.core.data.PostResult
import com.example.core.data.ViewState
import com.example.lifehub.features.todo.data.TodoData
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
    private val todoRepo: TodoRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id: String? = savedStateHandle[NavArgumentType.ID.label]

    private val _items = MutableStateFlow<ViewState<TodoData>>(ViewState.Loading)
    val items: StateFlow<ViewState<TodoData>> = _items

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _postResult = MutableStateFlow<PostResult<Unit>?>(null)
    val postResult: StateFlow<PostResult<Unit>?> = _postResult

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _items.value = ViewState.Loading
            _items.value = todoRepo.getTodoById(id)
        }
    }

    fun updateText(id: String, newText: String) {
        _items.update { state ->
            when (state) {
                is ViewState.Content -> {
                    val updatedItems = state.data.items.map { item ->
                        if (item.id == id) item.copy(text = newText) else item
                    }
                    ViewState.Content(
                        state.data.copy(items = updatedItems)
                    )
                }

                else -> state
            }
        }
    }

    fun updateChecked(id: String, isChecked: Boolean) {
        _items.update { state ->
            when (state) {
                is ViewState.Content -> {
                    val updatedItems = state.data.items.map { item ->
                        if (item.id == id) item.copy(isComplete = isChecked) else item
                    }
                    ViewState.Content(
                        state.data.copy(items = updatedItems)
                    )
                }

                else -> state
            }
        }
    }

    fun move(from: Int, to: Int) {
        _items.update { state ->
            when (state) {
                is ViewState.Content -> {
                    val currentItems = state.data.items.toMutableList()
                    val item = currentItems.removeAt(from)
                    currentItems.add(to, item)

                    ViewState.Content(
                        state.data.copy(items = currentItems)
                    )
                }

                else -> state
            }
        }
    }

    fun addItem() {
        _items.update { state ->
            when (state) {
                is ViewState.Content -> {
                    val updatedItems = state.data.items + TodoItem()
                    ViewState.Content(
                        state.data.copy(items = updatedItems)
                    )
                }

                else -> state
            }
        }
    }

    fun deleteItem(id: String) {
        _items.update { state ->
            when (state) {
                is ViewState.Content -> {
                    val updatedItems = state.data.items.filterNot { it.id == id }
                    ViewState.Content(
                        state.data.copy(items = updatedItems)
                    )
                }

                else -> state
            }
        }
    }

    fun updateDate(date: Long) {
        _items.update { state ->
            when (state) {
                is ViewState.Content -> {
                    ViewState.Content(
                        state.data.copy(date = date)
                    )
                }

                else -> state
            }
        }
    }

    fun validateSave(): Boolean {
        val state = _items.value
        return when (state) {
            is ViewState.Content -> {
                state.data.items.isNotEmpty() && state.data.items.all { it.text.isNotEmpty() }
            }

            else -> false
        }
    }

    fun save() {
        viewModelScope.launch {
            _isLoading.value = true
            val state = _items.value
            if (state is ViewState.Content) {
                _postResult.value = todoRepo.saveTodos(state.data)
            }
        }
        _isLoading.value = false
    }
}