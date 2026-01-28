package com.ntc.shopree.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RemoveFilled: ImageVector
    get() {
        if (_RemoveFilled != null) {
            return _RemoveFilled!!
        }
        _RemoveFilled = ImageVector.Builder(
            name = "RemoveFilled",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveTo(200f, 520f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(560f)
                verticalLineToRelative(80f)
                lineTo(200f, 520f)
                close()
            }
        }.build()

        return _RemoveFilled!!
    }

@Suppress("ObjectPropertyName")
private var _RemoveFilled: ImageVector? = null
