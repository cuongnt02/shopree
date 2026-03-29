package com.ntc.shopree.feature.checkout.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ntc.shopree.core.ui.theme.MobileAppTheme
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.fontSize5
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.R as CoreUiR

@Composable
fun OrderItem(
    productName: String,
    quantity: Int,
    price: Double,
    productImage: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = productImage,
            contentDescription = "checkout product image",
            modifier = Modifier.width(64.dp)
        )
        Column {
            Text(
                text = productName,
                fontSize = fontSize5,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Outfit
            )
            Text(text = "x$quantity", fontFamily = Outfit, fontSize = fontSize3)
        }
        // TODO: Currency localization ?
        Text(
            text = "${"%.0f".format(price * quantity)} VND",
            fontSize = fontSize5,
            fontFamily = Outfit,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Visible
        )
    }
    HorizontalDivider()
}

@Composable
fun OrderItem(
    productName: String,
    quantity: Int,
    price: Double,
    productImage: Painter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing1),
        horizontalArrangement = Arrangement.spacedBy(spacing2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = productImage, contentDescription = "checkout product image")
        Column {
            Text(
                text = productName,
                fontSize = fontSize5,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Outfit
            )
            Text(text = "x$quantity", fontFamily = Outfit, fontSize = fontSize3)
        }
        // TODO: Currency localization ?
        Text(
            text = "${"%.0f".format(price * quantity)} VND",
            fontSize = fontSize5,
            fontFamily = Outfit,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Visible
        )
    }
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
private fun OrderItemPreview() {
    MobileAppTheme {
        OrderItem(
            productName = "Sample Product",
            quantity = 2,
            price = 1000000000.0,
            productImage = painterResource(
                CoreUiR.drawable.demo
            )
        )
    }
}