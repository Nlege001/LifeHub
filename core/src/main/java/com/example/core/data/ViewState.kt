package com.example.core.data

/**
 * Reusable sealed class for representing UI states
 **/
sealed class ViewState<out T> {

    object Loading : ViewState<Nothing>()

    data class Error(
        val message: String = ""
    ) : ViewState<Nothing>()

    data class Content<out T>(
        val data: T
    ) : ViewState<T>()

}