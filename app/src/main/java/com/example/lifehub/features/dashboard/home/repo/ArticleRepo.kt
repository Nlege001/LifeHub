package com.example.lifehub.features.dashboard.home.repo

import com.example.core.data.ViewState
import com.example.core.utils.fetch
import com.example.lifehub.network.articles.data.Article
import com.example.lifehub.network.articles.network.NewsService
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@ViewModelScoped
class ArticleRepo @Inject constructor(
    private val newsService: NewsService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getNews(
        count: Int = ARTICLE_COUNT
    ): ViewState<List<Article>> {
        val result = fetch(
            api = { newsService.getNews() },
            ioDispatcher = ioDispatcher
        )?.results?.take(count)

        return if (result == null) {
            ViewState.Error()
        } else {
            ViewState.Content(result)
        }
    }

    companion object {
        const val ARTICLE_COUNT = 5
    }
}