package com.ntc.shopree.feature.cart.ui

import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.ProductVariant
import com.ntc.shopree.core.ui.components.Badge
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.core.ui.theme.MobileAppTheme
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.Red500
import com.ntc.shopree.core.ui.theme.fontSize1
import com.ntc.shopree.core.ui.theme.fontSize2
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.fontSize5
import com.ntc.shopree.core.ui.theme.radius1
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.theme.spacing4
import com.ntc.shopree.core.ui.R as CoreUiR

@Composable
fun CartItem(
    cartItem: CartItem,
    product: Product?,
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    onDetailsClick: (String) -> Unit,
    onIncrementCartItem: (CartItem) -> Unit,
    onDecrementCartItem: (CartItem) -> Unit,
    onRemoveCartItem: (CartItem) -> Unit,
    onVariantChange: (String) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(radius1),
        border = BorderStroke(1.dp, ColorGrey700),
        colors = CardDefaults.cardColors(containerColor = ColorGrey100)
    ) {
        Column(modifier = Modifier.padding(spacing4)) {
            Row(verticalAlignment = Alignment.Top) {
                val imageModifier = Modifier.size(100.dp)
                if (painter != null) {
                    Image(
                        painter = painter,
                        contentDescription = "product ${cartItem.productSlug} image",
                        modifier = imageModifier
                    )
                } else {
                    AsyncImage(
                        model = cartItem.mainImage,
                        contentDescription = "product ${cartItem.productSlug} image",
                        modifier = imageModifier
                    )
                }
                Spacer(modifier = Modifier.width(spacing3))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = cartItem.productName,
                        fontFamily = Outfit,
                        fontSize = fontSize5,
                        fontWeight = FontWeight.SemiBold,
                        color = ColorGrey700,
                    )
                    Spacer(modifier = Modifier.height(spacing2))
                    Text(
                        text = cartItem.variantName,
                        fontSize = fontSize1,
                        color = ColorGrey500
                    )


                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$${cartItem.price}",
                        fontFamily = Outfit,
                        fontSize = fontSize5,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(spacing4))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove quantity",
                            modifier = Modifier.clickable {
                                onDecrementCartItem(cartItem)
                            })
                        Text(
                            text = cartItem.quantity.toString(),
                            modifier = Modifier.padding(horizontal = spacing1),
                            fontSize = fontSize3,
                            fontFamily = Outfit
                        )
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add quantity",
                            modifier = Modifier.clickable {
                                onIncrementCartItem(cartItem)
                            })
                    }
                }


            }
            if (product != null && product.variants.size > 1) {
                Spacer(modifier = Modifier.height(spacing2))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    product.variants.forEach { variant ->
                        val isSelected = variant.id == cartItem.variantId
                        val variantColors = if (isSelected) ColorGrey700 else ColorGrey400
                        Badge(
                            onClick = { if (!isSelected) onVariantChange(variant.id) },
                            text = variant.title ?: "Default",
                            modifier = Modifier,
                            color = variantColors,
                            enabled = true,
                        )
                    }
                }
            }

            Text(
                text = "Remove",
                color = Red500,
                fontSize = fontSize3,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Outfit,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onRemoveCartItem(cartItem) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    MobileAppTheme {
        CartItem(
            cartItem = CartItem(
            productSlug = "iphone-15",
            vendorName = "Apple",
            variantId = "v1",
            variantName = "128GB, Blue",
            productName = "iPhone 15",
            quantity = 1,
            price = 799.0,
            mainImage = ""
        ),
            product = Product(
                id = "p1",
                title = "iPhone 15",
                slug = "iphone-15",
                vendorName = "Apple",
                description = "The latest iPhone",
                mainImage = "",
                variants = listOf(
                    ProductVariant(
                        id = "v1",
                        title = "128GB, Blue",
                        sku = "SKU1",
                        priceCents = 79900,
                        compareAtCents = 89900,
                        inventoryCount = 10
                    ), ProductVariant(
                        id = "v2",
                        title = "256GB, Black",
                        sku = "SKU2",
                        priceCents = 89900,
                        compareAtCents = 99900,
                        inventoryCount = 5
                    )
                )
            ),
            onDetailsClick = {},
            onIncrementCartItem = {},
            onDecrementCartItem = {},
            onRemoveCartItem = {},
            onVariantChange = {},
            painter = painterResource(CoreUiR.drawable.large_image_demo)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreviewSmall() {
    MobileAppTheme {
        CartItem(
            cartItem = CartItem(
            productSlug = "iphone-15",
            vendorName = "Apple",
            variantId = "v1",
            variantName = "128GB, Blue",
            productName = "iPhone 15",
            quantity = 1,
            price = 799.0,
            mainImage = ""
        ),
            product = Product(
                id = "p1",
                title = "iPhone 15",
                slug = "iphone-15",
                vendorName = "Apple",
                description = "The latest iPhone",
                mainImage = "",
                variants = listOf(
                    ProductVariant(
                        id = "v1",
                        title = "128GB, Blue",
                        sku = "SKU1",
                        priceCents = 79900,
                        compareAtCents = 89900,
                        inventoryCount = 10
                    ), ProductVariant(
                        id = "v2",
                        title = "256GB, Black",
                        sku = "SKU2",
                        priceCents = 89900,
                        compareAtCents = 99900,
                        inventoryCount = 5
                    )
                )
            ),
            onDetailsClick = {},
            onIncrementCartItem = {},
            onDecrementCartItem = {},
            onRemoveCartItem = {},
            onVariantChange = {},
            painter = painterResource(CoreUiR.drawable.demo)
        )
    }
}
