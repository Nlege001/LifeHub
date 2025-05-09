package com.example.lifehub.network.articles.di

import com.example.lifehub.network.articles.network.NewsApiKeyInterceptor
import com.example.lifehub.network.articles.network.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    private val baseUrl = "https://newsdata.io/api/1/"

    @Provides
    @NewsRetrofit
    fun provideNewsApiKeyInterceptor(): NewsApiKeyInterceptor = NewsApiKeyInterceptor()

    @Provides
    @NewsRetrofit
    fun provideOkhttp(
        @NewsRetrofit interceptor: NewsApiKeyInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @NewsRetrofit
    fun provideRetrofit(
        @NewsRetrofit client: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideNewsService(
        @NewsRetrofit retrofit: Retrofit
    ): NewsService = retrofit.create(NewsService::class.java)
}