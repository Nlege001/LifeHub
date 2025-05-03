package com.example.lifehub.features.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.OutlinedTextButton
import com.example.core.composables.PrimaryButton
import com.example.core.composables.TextButtonWithIcon
import com.example.core.composables.ViewStateCoordinator
import com.example.core.data.ProfilePictureChangeDirection
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.ImageUtils
import com.example.core.utils.toReadableDate
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd2
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.profile.data.ProfileData
import com.example.lifehub.features.profile.data.ProfilePictureOptions
import com.example.wpinterviewpractice.R
import java.io.File

private val page = Page.PROFILE

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    ViewStateCoordinator(
        state = viewModel.profile,
        refresh = { viewModel.getProfile() },
        page = page
    ) {
        Content(
            data = it,
            onChangePassword = {},
            onSignOut = {},
            onDeleteAccount = {},
            uploadProfilePicture = { userId, url, intent ->
                val bytes = ImageUtils.uriToByteArray(context, url)
                if (bytes != null) {
                    when (intent) {
                        ProfilePictureChangeDirection.CHANGE_PROFILE_PICTURE -> {
                            viewModel.uploadBackgroundPicture(userId, bytes)
                        }

                        ProfilePictureChangeDirection.CHANGE_BACKGROUND_PICTURE -> {
                            viewModel.uploadProfilePicture(userId, bytes)
                        }
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    data: ProfileData,
    onChangePassword: () -> Unit,
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit,
    uploadProfilePicture: (
        userId: String,
        url: Uri,
        intent: ProfilePictureChangeDirection,
    ) -> Unit,
    startWithEditSheetOpen: Boolean = false
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val showSheet = remember { mutableStateOf(startWithEditSheetOpen) }

    val context = LocalContext.current
    val photoFile = remember {
        File(context.cacheDir, "${data.userId}_profilePic.jpg")
    }
    val photoUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        photoFile
    )

    val capturedImageUri = remember { mutableStateOf<Uri?>(null) }
    val pictureIntent = remember { mutableStateOf<ProfilePictureChangeDirection?>(null) }
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            capturedImageUri.value = photoUri
            val image = capturedImageUri.value
            val intent = pictureIntent.value
            if (image != null && intent != null) {
                uploadProfilePicture(data.userId, image, intent)
            }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                takePhotoLauncher.launch(photoUri)
            } else {
                // Optional: show a snackbar or toast for denied permission
            }
        }
    )

    val pickGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        val image = uri
        val intent = pictureIntent.value
        if (image != null && intent != null) {
            uploadProfilePicture(data.userId, image, intent)
        }
    }

    Column(
        modifier = Modifier
            .background(Colors.Black)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProfilePicture(
            profilePictureUrl = data.profilePictureUrl,
            profileBackGroundPictureUrl = data.profileBackGroundPictureUrl,
            onEditBackgroundPicture = {
                pictureIntent.value = ProfilePictureChangeDirection.CHANGE_BACKGROUND_PICTURE
                showSheet.value = true
            },
            onEditProfilePicture = {
                pictureIntent.value = ProfilePictureChangeDirection.CHANGE_PROFILE_PICTURE
                showSheet.value = true
            }
        )

        // Member since badge
        Text(
            text = stringResource(R.string.member_since, data.memberSince),
            style = LifeHubTypography.labelMedium,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = pd4)
        )

        // User Info
        Column(modifier = Modifier.padding(horizontal = pd16, vertical = pd16)) {
            InfoRow(R.string.first_name, data.firstName)
            InfoRow(R.string.last_name, data.lastName)
            InfoRow(R.string.email, data.email)
            InfoRow(R.string.dob, data.dob.toReadableDate())
        }

        Spacer(modifier = Modifier.weight(1f))

        // Actions
        Column(
            modifier = Modifier.padding(
                horizontal = pd16
            ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PrimaryButton(
                modifier = Modifier
                    .padding(bottom = pd8)
                    .fillMaxWidth(),
                label = stringResource(R.string.change_email),
                onClick = onChangePassword,
                screen = page
            )
            OutlinedTextButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.sign_out),
                onClick = onSignOut,
                screen = page
            )
            ProfileActionButton(
                text = stringResource(R.string.delete_account),
                onClick = onDeleteAccount,
                textColor = Colors.Lavender
            )
        }
    }

    if (showSheet.value) {
        ChangeProfilePictureBottomSheet(
            sheetState = sheetState,
            onTakePhoto = {
                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            },
            onPickFromGallery = {
                pickGalleryLauncher.launch("image/*")
            },
            onDismiss = { showSheet.value = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangeProfilePictureBottomSheet(
    sheetState: SheetState,
    onTakePhoto: () -> Unit,
    onPickFromGallery: () -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = Color.DarkGray,
        contentColor = Colors.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = pd16, vertical = pd16)
        ) {
            // Title
            Text(
                text = stringResource(R.string.profile_picture_change),
                style = LifeHubTypography.headlineSmall,
                color = Colors.White,
                modifier = Modifier.padding(bottom = pd16)
            )

            // Options
            val profileOptions = ProfilePictureOptions.entries
            profileOptions.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .clickable {
                            when (item) {
                                ProfilePictureOptions.OPEN_GALLERY -> onPickFromGallery()
                                ProfilePictureOptions.TAKE_PICTURE -> onTakePhoto()
                            }
                        }
                        .fillMaxWidth()
                        .padding(vertical = pd16),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = pd16)
                            .size(pd24),
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.text),
                        tint = Colors.Lavender
                    )

                    Text(
                        text = stringResource(item.text),
                        style = LifeHubTypography.labelLarge,
                        color = Colors.White,
                    )
                }

                if (index != profileOptions.lastIndex) {
                    HorizontalDivider(
                        thickness = pd2,
                        color = Color.Gray.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: Int, value: String?) {
    Column(modifier = Modifier.padding(vertical = pd4)) {
        value?.let {
            Text(
                text = stringResource(label),
                style = LifeHubTypography.labelMedium,
                color = Color.Gray
            )
            Text(text = it, style = LifeHubTypography.labelLarge, color = Colors.White)
        }
    }
}

@Composable
fun ProfileActionButton(
    text: String,
    onClick: () -> Unit,
    textColor: Color = Colors.Lavender
) {
    TextButtonWithIcon(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = pd4),
        label = text,
        textColor = textColor,
        painter = null,
        textStyle = LifeHubTypography.bodySmall
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val mockProfile = ProfileData(
        profileBackGroundPictureUrl = null,
        profilePictureUrl = null,
        firstName = "Naol",
        lastName = "Legesse",
        email = "naol@example.com",
        dob = 892252800000,
        memberSince = "May 1, 2023",
        userId = ""
    )

    Content(
        data = mockProfile,
        onChangePassword = {},
        onSignOut = {},
        onDeleteAccount = {},
        uploadProfilePicture = { _, _, _ -> }
    )
}