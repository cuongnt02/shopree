package com.ntc.shopree.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.ntc.shopree.ui.icons.Icons
import com.ntc.shopree.ui.theme.ColorGrey100
import com.ntc.shopree.ui.theme.ColorGrey200
import com.ntc.shopree.ui.theme.ColorGrey300
import com.ntc.shopree.ui.theme.ColorGrey500

@Composable
fun TextInput(modifier: Modifier = Modifier) {
    var focusedIcon by remember { mutableStateOf(false) }
    OutlinedTextField(
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
        placeholder = { Text(text = "Enter your name") },
        leadingIcon = { Icon(Icons.Filled.Heart, contentDescription = null) },
        trailingIcon = {
            Icon(
                imageVector = if (focusedIcon) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                contentDescription = null,
                modifier = Modifier.clickable(
                    enabled = true,
                    role = Role.Button,
                    onClick = { focusedIcon = !focusedIcon })
            )
        }

    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun TextInputPreview() {
    TextInput()
}