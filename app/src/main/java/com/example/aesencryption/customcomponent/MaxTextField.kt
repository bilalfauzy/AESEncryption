package com.example.aesencryption.customcomponent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun MaxTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    readOnly: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    enabled: Boolean = true,
    leadingIcon: @Composable () -> Unit
) {

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.body1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        isError = isError,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        leadingIcon = leadingIcon,
        maxLines = 10,
        readOnly = readOnly
    )

}
