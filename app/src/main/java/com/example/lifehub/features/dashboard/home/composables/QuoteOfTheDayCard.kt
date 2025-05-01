package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd20
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd8
import com.example.lifehub.network.quoteoftheday.data.QuoteOfTheDay
import com.example.wpinterviewpractice.R

@Composable
fun QuoteOfTheDayCard(
    quote: QuoteOfTheDay,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(pd16)
            .fillMaxWidth(),
        shape = RoundedCornerShape(pd20),
        colors = CardDefaults.cardColors(
            containerColor = Colors.Charcoal,
        ),
        elevation = CardDefaults.cardElevation(pd8)
    ) {
        Column(
            modifier = Modifier
                .padding(pd24)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(pd16)
        ) {
            Text(
                text = stringResource(R.string.qod),
                style = LifeHubTypography.labelMedium,
                color = Colors.Lavender
            )

            Text(
                text = stringResource(R.string.quote, quote.q),
                style = LifeHubTypography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.quote_author, quote.a),
                    style = LifeHubTypography.bodyMedium,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewQuoteCard() {
    QuoteOfTheDayCard(
        quote = QuoteOfTheDay(
            q = "The mind is everything. What you think you become.",
            a = "Buddha",
            h = ""
        )
    )
}