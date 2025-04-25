package com.example.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.values.Colors

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Colors.Black)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo()
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingView() {
    LoadingView()
}