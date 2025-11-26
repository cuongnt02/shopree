package com.ntc.shopree.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.ntc.shopree.ui.icons.Icons
import com.ntc.shopree.ui.theme.ColorGrey100
import com.ntc.shopree.ui.theme.ColorGrey200
import com.ntc.shopree.ui.theme.ColorGrey500

@Composable
fun TextInput(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier,
        state = rememberTextFieldState(),
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
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun TextInputPreview() {
    TextInput(state = rememberTextFieldState(), placeholder = "Search")
}