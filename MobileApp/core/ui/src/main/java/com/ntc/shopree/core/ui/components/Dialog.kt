package com.ntc.shopree.core.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ShopreeAlertDialog(modifier: Modifier = Modifier,
                onDissmissRequest: () -> Unit,
                onConfirmation: () -> Unit,
                title: String,
                text: String,
                icon: ImageVector) {
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = title)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        onDismissRequest = {
            onDissmissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmation()
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDissmissRequest()
            }) {
                Text(text = "Dismiss")
            }
        }

    )

}