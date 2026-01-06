package com.ntc.shopree.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PasswordFilled: ImageVector
    get() {
        if (_PasswordFilled != null) {
            return _PasswordFilled!!
        }
        _PasswordFilled = ImageVector.Builder(
            name = "PasswordFilled",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(80f, 760f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(800f)
                verticalLineToRelative(80f)
                lineTo(80f, 760f)
                close()
                moveTo(126f, 518f)
                lineTo(74f, 488f)
                lineTo(108f, 428f)
                lineTo(40f, 428f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(68f)
                lineToRelative(-34f, -58f)
                lineToRelative(52f, -30f)
                lineToRelative(34f, 58f)
                lineToRelative(34f, -58f)
                lineToRelative(52f, 30f)
                lineToRelative(-34f, 58f)
                horizontalLineToRelative(68f)
                verticalLineToRelative(60f)
                horizontalLineToRelative(-68f)
                lineToRelative(34f, 60f)
                lineToRelative(-52f, 30f)
                lineToRelative(-34f, -60f)
                lineToRelative(-34f, 60f)
                close()
                moveTo(446f, 518f)
                lineTo(394f, 488f)
                lineTo(428f, 428f)
                horizontalLineToRelative(-68f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(68f)
                lineToRelative(-34f, -58f)
                lineToRelative(52f, -30f)
                lineToRelative(34f, 58f)
                lineToRelative(34f, -58f)
                lineToRelative(52f, 30f)
                lineToRelative(-34f, 58f)
                horizontalLineToRelative(68f)
                verticalLineToRelative(60f)
                horizontalLineToRelative(-68f)
                lineToRelative(34f, 60f)
                lineToRelative(-52f, 30f)
                lineToRelative(-34f, -60f)
                lineToRelative(-34f, 60f)
                close()
                moveTo(766f, 518f)
                lineTo(714f, 488f)
                lineTo(748f, 428f)
                horizontalLineToRelative(-68f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(68f)
                lineToRelative(-34f, -58f)
                lineToRelative(52f, -30f)
                lineToRelative(34f, 58f)
                lineToRelative(34f, -58f)
                lineToRelative(52f, 30f)
                lineToRelative(-34f, 58f)
                horizontalLineToRelative(68f)
                verticalLineToRelative(60f)
                horizontalLineToRelative(-68f)
                lineToRelative(34f, 60f)
                lineToRelative(-52f, 30f)
                lineToRelative(-34f, -60f)
                lineToRelative(-34f, 60f)
                close()
            }
        }.build()

        return _PasswordFilled!!
    }

@Suppress("ObjectPropertyName")
private var _PasswordFilled: ImageVector? = null
