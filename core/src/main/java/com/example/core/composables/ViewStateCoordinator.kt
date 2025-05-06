package com.example.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.data.PostResult
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
    TrackScreenSeen(page)
    when (val value = state.collectAsState().value) {
        is ViewState.Content -> content(value.data)
        is ViewState.Error -> errorComposable()
        ViewState.Loading -> loadingComposable()
    }
}

@Composable
fun <FetchResult, PostData> ViewStateCoordinator(
    modifier: Modifier = Modifier,
    state: StateFlow<ViewState<FetchResult>>,
    post: StateFlow<PostResult<PostData>?>,
    isLoading: StateFlow<Boolean>,
    page: Page,
    refresh: () -> Unit,
    loadingComposable: @Composable () -> Unit = { LoadingView() },
    errorComposable: @Composable () -> Unit = { ErrorView(refresh = refresh, page = page) },
    content: @Composable (FetchResult, PostResult<PostData>?, Boolean) -> Unit
) {

    val viewState = state.collectAsState().value
    val postResult = post.collectAsState().value
    val isLoading = isLoading.collectAsState().value

    Box(modifier = modifier.fillMaxSize()){
        when (viewState) {
            is ViewState.Content -> {
                val data = viewState.data
                content(data, postResult, isLoading)
            }

            is ViewState.Error -> errorComposable()
            ViewState.Loading -> loadingComposable()
        }
    }
}