package com.ntc.shopree.core.ui.components

import android.graphics.LinearGradient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.theme.ColorPrimary300
import com.ntc.shopree.core.ui.theme.ColorPrimary400
import com.ntc.shopree.core.ui.theme.ColorPrimary500

@Composable
fun PrimaryButton(
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onclick, modifier =modifier.background(MaterialTheme.colorScheme.primary, shape = CircleShape
        ), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        enabled = enabled
    ) {
            content()
        }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(onclick = {}) {
        Text("Primary Button")
    }
}
