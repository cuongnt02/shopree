package com.ntc.shopree.core.ui.extensions

import android.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.insetRing(
    color: Color,
    width: Dp = 1.dp,
    radius: Dp
): Modifier = this.drawWithContent{
    drawContent()
    drawRoundRect(
        color = color,
        topLeft = Offset(width.toPx(), width.toPx()),
        size = Size(size.width - width.toPx() * 2, size.height - width.toPx() * 2),
        cornerRadius = CornerRadius(radius.toPx()),
        style = Stroke(width = width.toPx())
    )
}