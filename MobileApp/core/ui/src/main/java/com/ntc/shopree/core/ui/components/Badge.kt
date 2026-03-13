package com.ntc.shopree.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.ntc.shopree.core.ui.extensions.insetRing
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.Transparent1
import com.ntc.shopree.core.ui.theme.fontSize1
import com.ntc.shopree.core.ui.theme.fontSize2
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.radius1

@Composable
fun Badge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ColorGrey400
) {
    Box(
        modifier
            .background(color.copy(alpha = 0.1f), shape = RoundedCornerShape(radius1))
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(radius1))
            .insetRing(color = color.copy(alpha = 0.2f), radius = radius1)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = fontSize3,
            color = color,
            fontFamily = Outfit,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Preview
@Composable
private fun BadgePreview() {
    Badge("Badge")
}