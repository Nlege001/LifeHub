package com.example.core.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.lifehub),
        contentDescription = stringResource(R.string.life_hub_logo)
    )
}