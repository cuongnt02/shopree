package com.ntc.shopree.feature.cart.ui

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize1
import com.ntc.shopree.core.ui.theme.radius1
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2

@Composable
fun CartButton(
    quantity: Int = 0,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.size(32.dp)) {
        Box(
            Modifier
                .background(color = ColorGrey100, shape = RoundedCornerShape(radius1))
                .border(
                    1.dp, ColorGrey700, RoundedCornerShape(radius1)
                )
        ) {
            IconButton(
                onClick = onNavigate, modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cart,
                    contentDescription = "Cart",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        if (quantity > 0) Box(
            modifier = Modifier
                .size(16.dp)
                .offset((-6).dp, (-6).dp)
                .background(
                    color = ColorGrey700, shape = CircleShape
                )
                .align(Alignment.TopStart), contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                color = ColorGrey100,
                fontSize = 10.sp,
                fontFamily = Outfit,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeight = 10.sp
                )
            )
        }
    }

}
