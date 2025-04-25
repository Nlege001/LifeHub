package com.example.core.data

/**
 * Class holding post result data from a post action
 **/
sealed class PostResult<out T> {

    data class Error(
        val message: String = ""
    ) : PostResult<Nothing>()

    data class Success<out T>(
        val data: T
    ) : PostResult<T>()

}