package com.ntc.shopree.core.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.theme.ColorGrey500

@Composable
fun TextInput(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    validatorHasErrors: Boolean = false,
    errorText: String = ""
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        enabled = true,
        singleLine = true,
        onValueChange = onValueChanged,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = ColorGrey200,
            unfocusedBorderColor = ColorGrey100,
            unfocusedTextColor = ColorGrey200,
            focusedContainerColor = ColorGrey200,
            focusedBorderColor = ColorGrey500,
            focusedTextColor = ColorGrey200,
            cursorColor = ColorGrey500,
        ),
        shape = CircleShape,
        placeholder = { Text(text = placeholder ?: "") },
        leadingIcon = { leading?.invoke() },
        trailingIcon = {
            trailing?.invoke()
        },
        supportingText = @Composable {
            if (validatorHasErrors) {
                Text(text = errorText)
            }
        },
        isError = validatorHasErrors
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun TextInputPreview() {
    TextInput(
        value = "Search", onValueChanged = {}, leading = {}, placeholder = "Search")
}
