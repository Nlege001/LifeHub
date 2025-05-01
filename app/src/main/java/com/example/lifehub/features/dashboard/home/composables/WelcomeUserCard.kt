package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd8
import com.example.wpinterviewpractice.R

@Composable
fun WelcomeUserCard(
    userName: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = pd16, vertical = pd8),
        color = Colors.Charcoal,
        shape = RoundedCornerShape(pd16),
        elevation = pd4,
    ) {
        Column(
            modifier = Modifier
                .padding(pd24),
            verticalArrangement = Arrangement.spacedBy(pd4)
        ) {
            Text(
                text = stringResource(R.string.welcome_back),
                style = LifeHubTypography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = userName,
                style = LifeHubTypography.headlineSmall,
                color = Colors.White
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewWelcomeUserCard() {
    WelcomeUserCard(userName = "Naol")
}