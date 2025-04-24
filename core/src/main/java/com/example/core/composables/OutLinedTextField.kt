package com.example.core.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OutLinedTextField(
    modifier: Modifier = Modifier,
    label: String?,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    singleLine: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier,
        label = {
            if (!label.isNullOrEmpty()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body1
                )
            }
        },
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewOutLinedTextField() {
    val text = rememberSaveable { mutableStateOf("") }
    OutLinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = "LogIn"
    )
}
