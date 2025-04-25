package com.example.core.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.core.analytics.Page
import com.example.core.data.ViewState
import kotlinx.coroutines.flow.StateFlow

/**
 * Reusable composable for managing screen states that involve network calls
 **/
@Composable
fun <T> ViewStateCoordinator(
    state: StateFlow<ViewState<T>>,
    page: Page,
    refresh: () -> Unit,
    loadingComposable: @Composable () -> Unit = { LoadingView() },
    errorComposable: @Composable () -> Unit = { ErrorView(refresh = refresh, page = page) },
    content: @Composable (T) -> Unit
) {
    when (val value = state.collectAsState().value) {
        is ViewState.Content -> content(value.data)
        is ViewState.Error -> errorComposable()
        ViewState.Loading -> loadingComposable()
    }
}