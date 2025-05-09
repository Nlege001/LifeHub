package com.example.lifehub.network.articles.data

data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Article>
)