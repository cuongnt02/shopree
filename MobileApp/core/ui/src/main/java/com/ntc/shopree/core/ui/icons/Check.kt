package com.ntc.shopree.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CheckOutlined: ImageVector
    get() {
        if (_CheckOutlined != null) {
            return _CheckOutlined!!
        }
        _CheckOutlined = ImageVector.Builder(
            name = "CheckOutlined",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveTo(382f, 720f)
                lineTo(154f, 492f)
                lineToRelative(57f, -57f)
                lineToRelative(171f, 171f)
                lineToRelative(367f, -367f)
                lineToRelative(57f, 57f)
                lineToRelative(-424f, 424f)
                close()
            }
        }.build()

        return _CheckOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _CheckOutlined: ImageVector? = null
