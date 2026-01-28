package com.ntc.shopree.feature.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100

@Composable
fun CartButton(onNavigate: () -> Unit, modifier: Modifier = Modifier) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val quantity = cartViewModel.quantity.collectAsState()
    Box(Modifier.background(color = ColorGrey100, shape = CircleShape)) {
        IconButton(
            onClick = onNavigate, modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Outlined.Cart, contentDescription = "Cart",
                modifier = Modifier.size(24.dp)
            )
        }
        Box(modifier = Modifier.background(color = Color.Black)) {
            Text(text = quantity.value.toString(), color = ColorGrey100)
        }
    }
}
