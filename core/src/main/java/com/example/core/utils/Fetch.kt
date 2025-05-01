package com.example.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> fetch(
    api: suspend () -> Response<T>,
    ioDispatcher: CoroutineDispatcher
): T? = withContext(ioDispatcher) {
    try {
        val response = api()
        if (!response.isSuccessful) return@withContext null
        response.body()
    } catch (e: Exception) {
        null
    }
}