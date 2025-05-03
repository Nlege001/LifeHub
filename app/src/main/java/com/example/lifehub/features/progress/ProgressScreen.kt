package com.example.lifehub.features.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.composables.CinematicTypingText
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import com.example.wpinterviewpractice.R

@Composable
fun ProgressScreen(navigate: (ProgressItems) -> Unit) {
    Content(navigate = navigate)
}

@Composable
private fun Content(
    navigate: (ProgressItems) -> Unit
) {

    val progressItems = ProgressItems.entries.chunked(2) // 2 items per row

    Column(
        modifier = Modifier
            .background(Color.DarkGray)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = pd16)
    ) {
        CinematicTypingText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = pd32),
            text = stringResource(R.string.progress_title),
            textAlign = TextAlign.Center,
            style = LifeHubTypography.titleLarge,
            color = Color.LightGray
        )

        Text(
            modifier = Modifier
                .padding(bottom = pd32)
                .fillMaxWidth(),
            text = stringResource(R.string.progress_subTitle),
            style = LifeHubTypography.titleSmall,
            color = Color.LightGray,
            textAlign = TextAlign.Center,
        )

        progressItems.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = pd16),
                horizontalArrangement = Arrangement.spacedBy(pd16)
            ) {
                rowItems.forEach { item ->
                    FeatureCard(
                        modifier = Modifier.weight(1f),
                        item = item,
                        onClick = { navigate(item) }
                    )
                }

                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewContent() {
    Content(
        navigate = {}
    )
}