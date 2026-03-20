package com.ntc.shopree.feature.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.ProductVariant
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.ShopreeAlertDialog
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.theme.MobileAppTheme
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.serialization.Serializable
import com.ntc.shopree.core.ui.R as CoreUiR


@Serializable
data object CartScreen : NavKey



@Composable
fun CartScreen(modifier: Modifier = Modifier, onBack: () -> Unit, onCheckout: () -> Unit) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val state by cartViewModel.uiState.collectAsState()
    val price by cartViewModel.totalPrice.collectAsState()

    CartScreenContent(
        state = state,
        price = price,
        modifier = modifier,
        onBack = onBack,
        onCheckout = onCheckout,
        onDecrementCartItem = { cartViewModel.decrementCartItem(it) },
        onIncrementCartItem = { cartViewModel.incrementCartItem(it) },
        onRemoveCartItem = { cartViewModel.removeCartItem(it) },
        onClearCart = { cartViewModel.clearCart() },
        onVariantChange = { item, product, variantId ->
            cartViewModel.updateVariant(item, product, variantId)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
    state: CartUiState,
    price: Double,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    onDecrementCartItem: (CartItem) -> Unit,
    onIncrementCartItem: (CartItem) -> Unit,
    onRemoveCartItem: (CartItem) -> Unit,
    onClearCart: () -> Unit,
    onVariantChange: (CartItem, Product, String) -> Unit,
    itemPainter: @Composable (CartItem) -> Painter? = { null }
) {
    var clearCartConfirm by remember { mutableStateOf(false) }

    Column {
        CenterAlignedTopAppBar(title = { Text("Your Cart") }, navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable {
                    onBack()
                })
        })
        when (state) {
            is CartUiState.Loading -> {
                CircularProgressIndicator()
            }

            is CartUiState.Success -> {
                val cartItems = state.cartItems
                val products = state.products
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LazyColumn(modifier = modifier.weight(1f)) {
                        items(cartItems.size) { index ->
                            val item = cartItems[index]
                            val product = products[item.productSlug]
                            CartItem(
                                cartItem = item,
                                product = product,
                                painter = itemPainter(item),
                                onDetailsClick = {
                                    // TODO: Navigate to product details
                                },
                                onDecrementCartItem = {
                                    onDecrementCartItem(item)
                                },
                                onIncrementCartItem = {
                                    onIncrementCartItem(item)
                                },
                                onRemoveCartItem = {
                                    onRemoveCartItem(item)
                                },
                                onVariantChange = { variantId ->
                                    if (product != null) {
                                        onVariantChange(item, product, variantId)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    PrimaryButton(
                        onclick = {
                            clearCartConfirm = true
                        }) {
                        Text(text = "Clear Cart")
                    }
                }
                if (clearCartConfirm) {
                    ShopreeAlertDialog(
                        title = "Clear Cart",
                        text = "Are you sure you want to clear your cart?",
                        icon = Icons.Outlined.Cart,
                        onDissmissRequest = {
                            clearCartConfirm = false
                        },
                        onConfirmation = {
                            onClearCart()
                            clearCartConfirm = false
                        })
                }


            }

            is CartUiState.Error -> {
                val message = state.message
                LaunchedEffect(message) {
                    SnackbarController.sendEvent(SnackbarEvent(message = message))
                }
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = "Total: $price", style = MaterialTheme.typography.titleLarge)
            PrimaryButton(onclick = { onCheckout()}) {
                Text(text = "Checkout", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

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
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ColorGrey200)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val imageModifier = Modifier.background(color = ColorGrey200, shape = CircleShape)
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
                Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                    Text(text = cartItem.productName, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Variant: ${cartItem.variantName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = "$${cartItem.price}", style = MaterialTheme.typography.labelLarge)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Remove quantity",
                        modifier = Modifier.clickable {
                            onDecrementCartItem(cartItem)
                        })
                    Text(text = cartItem.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add quantity",
                        modifier = Modifier.clickable {
                            onIncrementCartItem(cartItem)
                        })
                }
            }

            if (product != null && product.variants.size > 1) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Change variant:", style = MaterialTheme.typography.labelSmall)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    product.variants.forEach { variant ->
                        val isSelected = variant.id == cartItem.variantId
                        AssistChip(
                            onClick = { if (!isSelected) onVariantChange(variant.id) },
                            label = { Text(variant.title ?: "Default", style = MaterialTheme.typography.labelSmall) },
                            colors = if (isSelected) {
                                AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                            } else {
                                AssistChipDefaults.assistChipColors()
                            }
                        )
                    }
                }
            }

            Text(
                text = "Remove",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.End).clickable { onRemoveCartItem(cartItem) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    val sampleCartItems = listOf(
        CartItem(
            productSlug = "product-1",
            vendorName = "Vendor 1",
            variantId = "v1",
            variantName = "Small",
            productName = "Sample Product 1",
            quantity = 2,
            price = 10.0,
            mainImage = ""
        ),
        CartItem(
            productSlug = "product-2",
            vendorName = "Vendor 2",
            variantId = "v3",
            variantName = "Default",
            productName = "Sample Product 2",
            quantity = 1,
            price = 25.0,
            mainImage = ""
        )
    )
    val sampleProducts = mapOf(
        "product-1" to Product(
            id = "p1",
            title = "Sample Product 1",
            slug = "product-1",
            vendorName = "Vendor 1",
            description = "Description 1",
            mainImage = "",
            variants = listOf(
                ProductVariant("v1", "Small", "sku1", 1000, null, 10),
                ProductVariant("v2", "Large", "sku2", 1500, null, 5)
            )
        ),
        "product-2" to Product(
            id = "p2",
            title = "Sample Product 2",
            slug = "product-2",
            vendorName = "Vendor 2",
            description = "Description 2",
            mainImage = "",
            variants = listOf(
                ProductVariant("v3", "Default", "sku3", 2500, null, 20)
            )
        )
    )

    MobileAppTheme {
        CartScreenContent(
            state = CartUiState.Success(cartItems = sampleCartItems, products = sampleProducts),
            price = 45.0,
            onBack = {},
            onCheckout = {},
            onDecrementCartItem = {},
            onIncrementCartItem = {},
            onRemoveCartItem = {},
            onClearCart = {},
            onVariantChange = { _, _, _ -> },
            itemPainter = { item ->
                if (item.productSlug == "product-1") {
                    painterResource(CoreUiR.drawable.demo)
                } else {
                    painterResource(CoreUiR.drawable.large_image_demo)
                }
            }
        )
    }
}
