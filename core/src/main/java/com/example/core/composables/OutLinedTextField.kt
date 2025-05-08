package com.example.core.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors

@Composable
fun OutLinedTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LifeHubTypography.bodyMedium,
    label: String?,
    labelColor: Color = Color.White,
    value: String,
    errorLabel: String? = null,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = textStyle,
            label = {
                if (!label.isNullOrEmpty()) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.body1,
                        color = labelColor
                    )
                }
            },
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = singleLine,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = colors ?: TextFieldDefaults.outlinedTextFieldColors(
                textColor = Colors.White,
                focusedBorderColor = Colors.Indigo,
                unfocusedBorderColor = Colors.White,
            ),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

        errorLabel?.let {
            Text(
                text = it,
                style = LifeHubTypography.bodySmall,
                color = Color.Red
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewOutLinedTextField() {
    val text = rememberSaveable { mutableStateOf("") }
    OutLinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = "LogIn",
        errorLabel = "Custom error message"
    )
}
