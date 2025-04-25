package com.example.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.R
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    refresh: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Colors.Black)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo()
        Text(
            modifier = Modifier.padding(bottom = pd16),
            text = stringResource(R.string.generic_error),
            style = LifeHubTypography.headlineMedium,
            color = Colors.White
        )
        PrimaryButton(
            onClick = refresh,
            label = stringResource(R.string.refresh)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorView() {
    ErrorView(refresh = {})
}