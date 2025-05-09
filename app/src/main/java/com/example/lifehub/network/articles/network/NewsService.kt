package com.example.lifehub.network.articles.network

import com.example.lifehub.network.articles.data.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    companion object {

        enum class NewsQuery(val query: String) {
            SELF_CARE("self-care"),
            MINDFULNESS("mindfulness"),
            PERSONAL_DEVELOPMENT("personal development"),
        }

        val queryItems = listOf(
            NewsQuery.SELF_CARE,
            NewsQuery.MINDFULNESS,
            NewsQuery.PERSONAL_DEVELOPMENT
        ).joinToString(" ") { it.query }

        enum class NewsCategory(val category: String) {
            HEALTH("health"),
            LIFESTYLE("lifestyle")
        }

        val categoryItems = listOf(
            NewsCategory.HEALTH,
            NewsCategory.LIFESTYLE
        ).joinToString(",") { it.category }
    }

    @GET("news")
    suspend fun getNews(
        @Query("q") query: String = queryItems,
        @Query("language") language: String = "en",
        @Query("category") category: String = categoryItems
    ): Response<ArticleResponse>
}