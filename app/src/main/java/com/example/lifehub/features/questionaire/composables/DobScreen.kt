package com.example.lifehub.features.questionaire.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.CinematicTypingText
import com.example.core.composables.DatePicker
import com.example.core.composables.DatePickerErrorType
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.TextButtonWithIcon
import com.example.core.data.PostResult
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd12
import com.example.core.values.Dimens.pd16
import com.example.lifehub.features.questionaire.viewmodel.DobViewModel
import com.example.wpinterviewpractice.R

private val page = Page.FIRST_NAME_LAST_NAME

@Composable
fun DobScreen(
    viewModel: DobViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    TrackScreenSeen(page)
    val saveResult = viewModel.saveUserProfileResult.collectAsState().value

    LaunchedEffect(saveResult) {
        if (saveResult is PostResult.Success) {
            onSuccess()
        }
    }

    Content(
        onSubmit = { viewModel.savedUserProfile(it) },
        saveFailed = saveResult is PostResult.Error,
        isLoading = viewModel.isLoading.collectAsState().value
    )
}

@Composable
private fun Content(
    saveFailed: Boolean,
    onSubmit: (dob: Long) -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = Modifier
            .background(Colors.Black)
            .baseHorizontalMargin()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoAndAppTitle(
            modifier = Modifier.padding(top = pd16)
        )

        Spacer(modifier = Modifier.weight(1f))

        CinematicTypingText(
            modifier = Modifier.padding(pd16),
            text = stringResource(R.string.dob_title),
            textAlign = TextAlign.Center,
            color = Colors.White,
            style = LifeHubTypography.titleLarge
        )

        val shouldValidateInput = rememberSaveable { mutableStateOf(false) }
        val dob = rememberSaveable { mutableStateOf<Long?>(null) }
        DatePicker(
            modifier = Modifier.fillMaxWidth(),
            selectedDate = dob.value,
            onDateSelected = { dob.value = it },
            label = stringResource(R.string.dob),
            type = DatePickerErrorType.AGE
        )

        Spacer(modifier = Modifier.weight(1f))

        if (saveFailed) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(pd12))
                    .background(color = Color(0xFFFFE6E6))
                    .padding(vertical = pd12, horizontal = pd16)
            ) {
                Text(
                    text = stringResource(R.string.user_save_error),
                    color = Color.Red,
                    style = LifeHubTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        TextButtonWithIcon(
            modifier = Modifier.padding(vertical = pd16),
            label = stringResource(R.string.next),
            isLoading = isLoading,
            onClick = {
                shouldValidateInput.value = true
                dob.value?.let {
                    onSubmit(it)
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDobScreen() {
    Content(
        onSubmit = {},
        saveFailed = true,
        isLoading = false
    )
}