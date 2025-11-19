package com.ntc.shopree.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ntc.shopree.ui.icons.Icons
import com.ntc.shopree.ui.theme.ColorGrey200

@Composable
fun BottomNavigation() {
    BottomAppBar(modifier = Modifier.clip(shape = CircleShape), containerColor = ColorGrey200) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 7.dp, end = 7.dp),horizontalArrangement = Arrangement.SpaceBetween) {
            BottomNavigationItem(icon = Icons.Outlined.Home)
            BottomNavigationItem(icon = Icons.Outlined.Heart)
            BottomNavigationItem(icon = Icons.Outlined.Notifications)
            BottomNavigationItem(icon = Icons.Outlined.Notifications)
        }
    }
}

@Composable
fun BottomNavigationItem(icon: ImageVector, selectedIcon: ImageVector? = null) {
    Box(modifier = Modifier.size(60.dp).clip(shape = CircleShape).background(Color.White)) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(43.dp).align(Alignment.Center))
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF)
fun BottomNavigationPreview() {
    BottomNavigation()
}