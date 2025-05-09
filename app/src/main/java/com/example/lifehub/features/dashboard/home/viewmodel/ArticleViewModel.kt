package com.example.lifehub.features.dashboard.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.ViewState
import com.example.lifehub.features.dashboard.home.repo.ArticleRepo
import com.example.lifehub.network.articles.data.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repo: ArticleRepo
) : ViewModel() {

    private val _articles = MutableStateFlow<ViewState<List<Article>>>(ViewState.Loading)
    val articles: StateFlow<ViewState<List<Article>>> = _articles

    init {
        getArticles()
    }

    fun getArticles() {
        viewModelScope.launch {
            _articles.value = ViewState.Loading
            _articles.value = repo.getNews()
        }
    }
}