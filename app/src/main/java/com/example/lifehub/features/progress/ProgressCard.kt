package com.example.lifehub.features.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.example.core.composables.LottieWrapper
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Dimens.pd16

@Composable
fun FeatureCard(
    item: ProgressItems,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .size(300.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .border(1.dp, Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .padding(pd16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieWrapper(
            modifier = Modifier.size(130.dp),
            file = item.lottie,
            iterations = LottieConstants.IterateForever
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(item.title),
            style = LifeHubTypography.titleMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(item.subTitle),
            style = LifeHubTypography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeatureCard() {
    FeatureCard(
        item = ProgressItems.GOAL,
        onClick = {}
    )
}