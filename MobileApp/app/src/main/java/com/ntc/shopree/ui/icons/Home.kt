package com.ntc.shopree.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val HomeFilled: ImageVector
    get() {
        if (_HomeFilled != null) {
            return _HomeFilled!!
        }
        _HomeFilled = ImageVector.Builder(
            name = "HomeFilled",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(160f, 840f)
                verticalLineToRelative(-480f)
                lineToRelative(320f, -240f)
                lineToRelative(320f, 240f)
                verticalLineToRelative(480f)
                lineTo(560f, 840f)
                verticalLineToRelative(-280f)
                lineTo(400f, 560f)
                verticalLineToRelative(280f)
                lineTo(160f, 840f)
                close()
            }
        }.build()

        return _HomeFilled!!
    }

@Suppress("ObjectPropertyName")
private var _HomeFilled: ImageVector? = null


val HomeOutlined: ImageVector
    get() {
        if (_HomeOutlined != null) {
            return _HomeOutlined!!
        }
        _HomeOutlined = ImageVector.Builder(
            name = "HomeOutlined",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(240f, 760f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(240f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(-360f)
                lineTo(480f, 220f)
                lineTo(240f, 400f)
                verticalLineToRelative(360f)
                close()
                moveTo(160f, 840f)
                verticalLineToRelative(-480f)
                lineToRelative(320f, -240f)
                lineToRelative(320f, 240f)
                verticalLineToRelative(480f)
                lineTo(520f, 840f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(240f)
                lineTo(160f, 840f)
                close()
                moveTo(480f, 490f)
                close()
            }
        }.build()

        return _HomeOutlined!!
    }

@Suppress("ObjectPropertyName")
private var _HomeOutlined: ImageVector? = null
