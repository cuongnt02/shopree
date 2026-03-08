package com.ntc.shopree.core.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.LocalContentColor
import com.composeunstyled.UnstyledButton
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorGrey700

@Composable
fun PrimaryButton(
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
//    Button(
//        onClick = onclick, modifier =modifier.background(MaterialTheme.colorScheme.primary, shape = CircleShape
//        ), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
//        enabled = enabled
//    ) {
//            content()
//        }
    UnstyledButton(
        onclick,
        enabled,
        RectangleShape,
        Color.Unspecified,
        LocalContentColor.current,
        PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        Color.Unspecified,
        0.dp,
        modifier.background(
            ColorGrey700, shape = RoundedCornerShape(6.dp)
        ),
        Role.Button,
        LocalIndication.current,
        null,
        Arrangement.Center,
        Alignment.CenterVertically,
        { content() })
}

@Composable
fun PrimaryButton(
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    fontSize: TextUnit = 12.sp,
    loading: Boolean = false,
) {
    val verticalPadding = with(LocalDensity.current) {
        (fontSize.toPx() * 0.5f).toDp()
    }
    val horizontalPadding = with(LocalDensity.current) {
        (fontSize.toPx() * 1.25f).toDp()
    }

    UnstyledButton(
        onclick,
        enabled,
        RectangleShape,
        Color.Unspecified,
        LocalContentColor.current,
        PaddingValues(horizontal = horizontalPadding, vertical = verticalPadding),
        Color.Unspecified,
        0.dp,
        modifier.background(
            ColorGrey700, shape = RoundedCornerShape(6.dp)
        ),
        Role.Button,
        LocalIndication.current,
        null,
        Arrangement.Center,
        Alignment.CenterVertically,
        {
            if (loading) {
                CircularProgressIndicator()
            } else {
                Text(text, fontSize = fontSize, color = Color.White)
            }
        })
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(onclick = {}) {
        Text("Primary Button", fontSize = 12.sp, color = Color.White)
    }
}
