package com.ntc.shopree.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.Text
import com.composeunstyled.TextField
import com.composeunstyled.TextInput
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorGrey600
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.core.ui.theme.Red500
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.theme.spacing4
import com.ntc.shopree.core.ui.theme.spacing5

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
            unfocusedTextColor = ColorGrey700,
            focusedContainerColor = ColorGrey200,
            focusedBorderColor = ColorGrey500,
            focusedTextColor = ColorGrey700,
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

@Composable
fun TextInput(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    error: Boolean = false,
    errorText: String = "",
    placeholder: String? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    onUnfocused: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val inputBorderColor by animateColorAsState(
        targetValue = when {
            error && isFocused -> Red500
            error -> Red500
            isFocused -> ColorGrey500
            else -> ColorGrey400
        }, animationSpec = tween(
            durationMillis = 100
        )
    )
    TextField(
        state = state,
        singleLine = singleLine,
        modifier = Modifier.onFocusChanged {
            isFocused = it.isFocused
            if (!isFocused) {
                onUnfocused?.invoke()
                focusManager.clearFocus()
            }
        },
    ) {
        Column {
            TextInput(
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 12.dp,
                    bottom = 12.dp,
                    end = 12.dp
                ),
                modifier = Modifier.border(
                    width = 2.dp, color = inputBorderColor, shape = RoundedCornerShape(6.dp)
                ),
                placeholder = {
                    if (placeholder != null) Text(
                        text = placeholder, color = ColorGrey400
                    )
                },
                leading = { leading?.invoke() },
                trailing = {
                    if (error) {
                        // TODO: Use error icon as trailing
                    } else {
                        trailing?.invoke()
                    }
                }
            )
            Box(modifier = Modifier.height(spacing3)) {
                if (error) {
                    Text(
                        text = errorText,
                        fontSize = 16.sp,
                        color = Red500,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun TextInputPreview() {
    TextInput(
        value = "Search", onValueChanged = {}, leading = {}, placeholder = "Search")
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun TextInputUnroundedPreview() {
    val state = remember { TextFieldState() }
    TextInput(
        state = state, singleLine = true, placeholder = "Adam Smith"
    )

}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun TextInputErrorPreview() {
    val state = remember { TextFieldState() }
    TextInput(
        state = state, singleLine = true, placeholder = "Adam Smith", error = true, errorText = "Not a valid email address."
    )

}
