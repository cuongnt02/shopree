package com.ntc.shopree.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val GoogleFilled: ImageVector
    get() {
        if (_GoogleFilled != null) {
            return _GoogleFilled!!
        }
        _GoogleFilled = ImageVector.Builder(
            name = "GoogleFilled",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 20f,
            viewportHeight = 20f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                strokeLineWidth = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(19.822f, 8.004f)
                lineTo(10.211f, 8.004f)
                curveTo(10.211f, 9.003f, 10.211f, 11.002f, 10.205f, 12.001f)
                lineTo(15.774f, 12.001f)
                curveTo(15.561f, 13.001f, 14.804f, 14.4f, 13.735f, 15.105f)
                curveTo(13.734f, 15.104f, 13.733f, 15.111f, 13.731f, 15.11f)
                curveTo(12.31f, 16.048f, 10.434f, 16.261f, 9.041f, 15.981f)
                curveTo(6.858f, 15.548f, 5.13f, 13.964f, 4.429f, 11.954f)
                curveTo(4.433f, 11.951f, 4.436f, 11.923f, 4.439f, 11.921f)
                curveTo(4f, 10.673f, 4f, 9.003f, 4.439f, 8.004f)
                lineTo(4.438f, 8.004f)
                curveTo(5.004f, 6.167f, 6.784f, 4.491f, 8.97f, 4.032f)
                curveTo(10.728f, 3.659f, 12.712f, 4.063f, 14.171f, 5.428f)
                curveTo(14.365f, 5.238f, 16.856f, 2.806f, 17.043f, 2.608f)
                curveTo(12.058f, -1.907f, 4.077f, -0.318f, 1.09f, 5.511f)
                lineTo(1.089f, 5.511f)
                curveTo(1.089f, 5.511f, 1.09f, 5.511f, 1.084f, 5.522f)
                lineTo(1.084f, 5.522f)
                curveTo(-0.393f, 8.386f, -0.332f, 11.76f, 1.094f, 14.486f)
                curveTo(1.09f, 14.489f, 1.087f, 14.491f, 1.084f, 14.494f)
                curveTo(2.377f, 17.003f, 4.729f, 18.927f, 7.564f, 19.659f)
                curveTo(10.575f, 20.449f, 14.407f, 19.909f, 16.974f, 17.587f)
                curveTo(16.975f, 17.588f, 16.976f, 17.589f, 16.977f, 17.59f)
                curveTo(19.152f, 15.631f, 20.506f, 12.294f, 19.822f, 8.004f)
            }
        }.build()

        return _GoogleFilled!!
    }

@Suppress("ObjectPropertyName")
private var _GoogleFilled: ImageVector? = null
