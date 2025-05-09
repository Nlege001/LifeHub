package com.example.lifehub.network.articles.network

import com.example.wpinterviewpractice.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NewsApiKeyInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .addQueryParameter("apikey", BuildConfig.NEWS_API_KEY)
            .build()
        val newRequest = original.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}