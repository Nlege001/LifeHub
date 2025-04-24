package com.example.core.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.core.data.ViewState
import kotlinx.coroutines.flow.StateFlow

/**
 * Reusable composable for managing screen states that involve network calls
 **/
@Composable
fun <T> ViewStateCoordinator(
    state: StateFlow<ViewState<T>>,
    refresh: () -> Unit,
    loadingComposable: @Composable () -> Unit,
    errorComposable: @Composable (refresh: () -> Unit) -> Unit,
    content: @Composable (T) -> Unit
) {
    when (val value = state.collectAsState().value) {
        is ViewState.Content -> content(value.data)
        is ViewState.Error -> errorComposable(refresh)
        ViewState.Loading -> loadingComposable()
    }
}