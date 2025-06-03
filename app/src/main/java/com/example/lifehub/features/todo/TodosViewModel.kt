package com.example.lifehub.features.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ViewState
import com.example.lifehub.features.todo.data.TodoData
import com.example.lifehub.features.todo.network.TodoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val repo: TodoRepo
) : ViewModel() {

    private val _data = MutableStateFlow<ViewState<List<TodoData>>>(ViewState.Loading)
    val data: StateFlow<ViewState<List<TodoData>>> = _data

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _data.value = ViewState.Loading
            _data.value = repo.getTodos()
        }
    }
}