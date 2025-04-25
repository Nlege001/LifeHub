package com.example.core.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.R
import com.example.core.theme.LifeHubTypography

@Composable
fun LogoAndAppTitle(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo()
        Text(
            color = textColor,
            text = stringResource(R.string.life_hub),
            style = LifeHubTypography.displayLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLogoAndAppTitle() {
    LogoAndAppTitle()
}