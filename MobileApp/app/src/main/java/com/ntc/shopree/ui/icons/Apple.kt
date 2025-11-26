package com.ntc.shopree.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppleFilled: ImageVector
    get() {
        if (_AppleFilled != null) {
            return _AppleFilled!!
        }
        _AppleFilled = ImageVector.Builder(
            name = "AppleFilled",
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
                moveTo(13.071f, 3.193f)
                curveTo(13.8f, 2.348f, 14.291f, 1.171f, 14.157f, 0f)
                curveTo(13.106f, 0.04f, 11.835f, 0.671f, 11.082f, 1.515f)
                curveTo(10.405f, 2.264f, 9.815f, 3.461f, 9.974f, 4.609f)
                curveTo(11.146f, 4.696f, 12.342f, 4.039f, 13.071f, 3.193f)
                moveTo(15.699f, 10.625f)
                curveTo(15.728f, 13.652f, 18.47f, 14.659f, 18.5f, 14.672f)
                curveTo(18.478f, 14.743f, 18.062f, 16.107f, 17.056f, 17.517f)
                curveTo(16.185f, 18.735f, 15.282f, 19.948f, 13.86f, 19.974f)
                curveTo(12.462f, 19.999f, 12.012f, 19.18f, 10.413f, 19.18f)
                curveTo(8.816f, 19.18f, 8.316f, 19.948f, 6.994f, 19.999f)
                curveTo(5.62f, 20.048f, 4.574f, 18.681f, 3.697f, 17.467f)
                curveTo(1.903f, 14.984f, 0.533f, 10.45f, 2.373f, 7.39f)
                curveTo(3.288f, 5.871f, 4.921f, 4.908f, 6.694f, 4.884f)
                curveTo(8.042f, 4.859f, 9.315f, 5.753f, 10.139f, 5.753f)
                curveTo(10.964f, 5.753f, 12.511f, 4.678f, 14.137f, 4.836f)
                curveTo(14.817f, 4.863f, 16.728f, 5.099f, 17.955f, 6.82f)
                curveTo(17.856f, 6.879f, 15.675f, 8.095f, 15.699f, 10.625f)
            }
        }.build()

        return _AppleFilled!!
    }

@Suppress("ObjectPropertyName")
private var _AppleFilled: ImageVector? = null
