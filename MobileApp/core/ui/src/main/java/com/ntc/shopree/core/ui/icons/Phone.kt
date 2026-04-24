package com.ntc.shopree.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PhoneOutlined: ImageVector
    get() {
        if (_PhoneOutlined != null) {
            return _PhoneOutlined!!
        }
        _PhoneOutlined = ImageVector.Builder(
            name = "PhoneOutlined",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(798f, 920f)
                quadToRelative(-125f, 0f, -247f, -54.5f)
                reflectiveQuadTo(329f, 329f)
                quadTo(229f, 229f, 174.5f, 107f)
                reflectiveQuadTo(120f, 160f)
                quadToRelative(0f, -19f, 13f, -32f)
                reflectiveQuadToRelative(32f, -13f)
                horizontalLineToRelative(140f)
                quadToRelative(14f, 0f, 24f, 10f)
                reflectiveQuadToRelative(14f, 25f)
                lineToRelative(27f, 140f)
                quadToRelative(2f, 16f, -1f, 27f)
                reflectiveQuadToRelative(-11f, 19f)
                lineToRelative(-97f, 98f)
                quadToRelative(20f, 37f, 44.5f, 71.5f)
                reflectiveQuadTo(359f, 583f)
                quadToRelative(29f, 39f, 63f, 73f)
                reflectiveQuadToRelative(73f, 63f)
                lineToRelative(94f, -94f)
                quadToRelative(9f, -9f, 23.5f, -13.5f)
                reflectiveQuadTo(640f, 614f)
                lineToRelative(138f, 28f)
                quadToRelative(14f, 4f, 23f, 14.5f)
                reflectiveQuadToRelative(9f, 24.5f)
                verticalLineToRelative(136f)
                quadToRelative(0f, 19f, -13f, 32f)
                reflectiveQuadToRelative(-32f, 13f)
                close()
            }
        }.build()

        return _PhoneOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _PhoneOutlined: ImageVector? = null
