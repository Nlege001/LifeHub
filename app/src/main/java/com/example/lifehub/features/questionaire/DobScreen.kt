package com.example.lifehub.features.questionaire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.composables.CinematicTypingText
import com.example.core.composables.DatePicker
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.TextButtonWithIcon
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.wpinterviewpractice.R

@Composable
fun DobScreen(
    onSubmit: (dob: Long) -> Unit
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
            label = stringResource(R.string.dob)
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButtonWithIcon(
            modifier = Modifier.padding(vertical = pd16),
            label = stringResource(R.string.next),
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
    DobScreen(
        onSubmit = {}
    )
}