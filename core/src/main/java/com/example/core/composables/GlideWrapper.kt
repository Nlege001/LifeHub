package com.example.core.composables

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.core.R
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.ImageUtils
import com.example.core.values.Colors

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideWrapper(
    imageUrl: Any,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val model = when (imageUrl) {
        is ByteArray -> ImageUtils.fixRotation(imageUrl)
        else -> imageUrl
    }

    if (imageUrl is ByteArray) {
        Log.d("GlideWrapper", "Image byte array size: ${imageUrl.size}")
    }
    GlideImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        loading = placeholder {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Colors.Lavender)
            }
        },
        failure = placeholder {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.generic_error),
                    style = LifeHubTypography.bodyMedium,
                    color = Colors.White
                )
            }
        }
    )
}