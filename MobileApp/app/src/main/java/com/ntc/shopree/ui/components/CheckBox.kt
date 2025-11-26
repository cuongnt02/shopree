package com.ntc.shopree.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ntc.shopree.ui.icons.Icons
import com.ntc.shopree.ui.theme.ColorSecondary300

@Composable
fun CheckBox(
    selected: Boolean,
    onChecked: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    IconButton(onClick = onChecked, enabled = enabled, modifier = Modifier.size(24.dp)) {
        Icon(
            imageVector = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
            contentDescription = "check box icon",
            tint = ColorSecondary300,
            modifier = Modifier.background(Color.Transparent, shape = CircleShape)
        )
    }
}