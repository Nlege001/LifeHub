package com.example.lifehub.features.dashboard.home

import com.example.lifehub.network.quoteoftheday.data.QuoteOfTheDay

data class DashboardFeedData(
    val firstName: String?,
    val quoteOfTheDay: QuoteOfTheDay?
)