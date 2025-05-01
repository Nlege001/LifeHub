package com.example.lifehub.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.core.composables.GlideWrapper
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd100
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd2
import com.example.core.values.Dimens.pd200
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd4
import com.example.wpinterviewpractice.R

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    ppSize: Dp = pd100,
    pbSizeHeight: Dp = pd200,
    profilePictureUrl: String?,
    profileBackGroundPictureUrl: String?,
    onEditBackgroundPicture: () -> Unit,
    onEditProfilePicture: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(pbSizeHeight + (ppSize / 2))
    ) {
        GlideWrapper(
            modifier = Modifier
                .fillMaxWidth()
                .height(pbSizeHeight),
            contentDescription = stringResource(R.string.profile_background_picture),
            imageUrl = profileBackGroundPictureUrl ?: R.drawable.ic_profile_background
        )

        IconButton(
            onClick = onEditBackgroundPicture,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(pd16)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Background",
                tint = Color.White
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = pd16, y = -ppSize / 2)
                .size(ppSize)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = Color.LightGray, shape = CircleShape)
                    .border(width = pd2, color = Colors.Lavender, shape = CircleShape)
                    .clip(CircleShape)
            ) {
                GlideWrapper(
                    modifier = Modifier.matchParentSize(),
                    contentDescription = stringResource(R.string.profile_picture),
                    imageUrl = profilePictureUrl ?: R.drawable.ic_add_photo,
                    contentScale = ContentScale.Inside
                )
            }

            IconButton(
                onClick = onEditProfilePicture,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = -pd4, y = -pd4)
                    .background(Color.White, CircleShape)
                    .size(pd24)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile Picture",
                    tint = Colors.Lavender
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfilePicture() {
    ProfilePicture(
        profilePictureUrl = null,
        profileBackGroundPictureUrl = null,
        onEditBackgroundPicture = {},
        onEditProfilePicture = {}
    )
}