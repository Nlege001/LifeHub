package com.example.lifehub.features.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.core.composables.TextButtonWithIcon
import com.example.core.composables.ViewStateCoordinator
import com.example.lifehub.features.profile.data.ProfilePictureChangeDirection
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.ImageUtils
import com.example.core.utils.toReadableDate
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd2
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd4
import com.example.lifehub.features.dashboard.home.appbar.AppBarIcon
import com.example.lifehub.features.dashboard.home.appbar.LocalAppBarController
import com.example.lifehub.features.profile.data.ProfileActionItems
import com.example.lifehub.features.profile.data.ProfileBottomSheetItem
import com.example.lifehub.features.profile.data.ProfileData
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
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val showSheet = remember { mutableStateOf(false) }

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
    val bottomSheetItem = rememberSaveable { mutableStateOf<ProfileBottomSheetItem?>(null) }

    val appBar = LocalAppBarController.current
    LaunchedEffect(Unit) {
        appBar.actions = listOf(
            AppBarIcon(
                iconResId = R.drawable.ic_settings,
                contentDescription = "Open settings"
            ) {
                bottomSheetItem.value = ProfileBottomSheetItem.AccountOptions()
                showSheet.value = true
            }
        )
    }
    DisposableEffect(Unit) {
        onDispose { appBar.clear() }
    }

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
                bottomSheetItem.value = ProfileBottomSheetItem.ProfilePicture()
                showSheet.value = true
            },
            onEditProfilePicture = {
                pictureIntent.value = ProfilePictureChangeDirection.CHANGE_PROFILE_PICTURE
                bottomSheetItem.value = ProfileBottomSheetItem.ProfilePicture()
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
    }

    val sheetItem = bottomSheetItem.value
    if (showSheet.value && sheetItem != null) {
        ChangeProfilePictureBottomSheet(
            sheetState = sheetState,
            onTakePhoto = {
                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            },
            onPickFromGallery = {
                pickGalleryLauncher.launch("image/*")
            },
            onDismiss = { showSheet.value = false },
            onChangePassword = onChangePassword,
            onSignOut = onSignOut,
            onDeleteAccount = onDeleteAccount,
            data = sheetItem
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangeProfilePictureBottomSheet(
    sheetState: SheetState,
    data: ProfileBottomSheetItem,
    onTakePhoto: () -> Unit,
    onPickFromGallery: () -> Unit,
    onDismiss: () -> Unit,
    onChangePassword: () -> Unit,
    onSignOut: () -> Unit,
    onDeleteAccount: () -> Unit,
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
                text = stringResource(data.title),
                style = LifeHubTypography.headlineSmall,
                color = Colors.White,
                modifier = Modifier.padding(bottom = pd16)
            )

            // Options
            data.items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .clickable {
                            when (item) {
                                ProfileActionItems.OPEN_GALLERY -> onPickFromGallery()
                                ProfileActionItems.TAKE_PICTURE -> onTakePhoto()
                                ProfileActionItems.SIGN_OUT -> onSignOut()
                                ProfileActionItems.CHANGE_EMAIL -> onChangePassword()
                                ProfileActionItems.DELETE_ACCOUNT -> onDeleteAccount()
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

                if (index != data.items.lastIndex) {
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