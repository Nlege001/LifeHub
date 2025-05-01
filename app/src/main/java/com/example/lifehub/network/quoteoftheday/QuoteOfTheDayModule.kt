package com.example.lifehub.network.quoteoftheday

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class QuoteOfTheDayModule {

    private val BASE_URL = "https://zenquotes.io/api/"

    @Provides
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideQuoteOfTheDayService(retrofit: Retrofit): QuoteOfTheDayService =
        retrofit.create(QuoteOfTheDayService::class.java)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}