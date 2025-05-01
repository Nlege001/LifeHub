package com.example.lifehub.network.quoteoftheday

import com.example.lifehub.network.quoteoftheday.data.QuoteOfTheDay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuoteOfTheDayService {

    @GET("{type}")
    suspend fun getQuote(
        @Path("type") type: String
    ): Response<List<QuoteOfTheDay>>

}