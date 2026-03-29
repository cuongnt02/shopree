package com.ntc.shopree.feature.cart.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.model.AsyncState
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.ProductVariant
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.ShopreeAlertDialog
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.MobileAppTheme
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize5
import com.ntc.shopree.core.ui.theme.fontSize6
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.serialization.Serializable
import com.ntc.shopree.core.ui.R as CoreUiR


@Serializable
data object CartScreen : NavKey


@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    onProductClick: (String) -> Unit
) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val state by cartViewModel.uiState.collectAsStateWithLifecycle()
    val price by cartViewModel.totalPrice.collectAsStateWithLifecycle()

    ObserveAsEvents(cartViewModel.events) { event ->
        when (event) {
            is CartEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    CartScreenContent(
        state = state,
        price = price,
        modifier = modifier,
        onBack = onBack,
        onCheckout = onCheckout,
        onProductClick = onProductClick,
        onDecrementCartItem = { cartViewModel.decrementCartItem(it) },
        onIncrementCartItem = { cartViewModel.incrementCartItem(it) },
        onRemoveCartItem = { cartViewModel.removeCartItem(it) },
        onClearCart = { cartViewModel.clearCart() },
        onVariantChange = { item, product, variantId ->
            cartViewModel.updateVariant(item, product, variantId)
        })
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
    onProductClick: (String) -> Unit,
    onVariantChange: (CartItem, Product, String) -> Unit,
    itemPainter: @Composable (CartItem) -> Painter? = { null }
) {
    var clearCartConfirm by remember { mutableStateOf(false) }

    Column {
        CenterAlignedTopAppBar(title = { Text("Your Cart") }, navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBack() })
        })
        when (val cartState = state.cartState) {
            is AsyncState.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncState.Success -> {
                val cartItems = cartState.data.items
                val products = cartState.data.products
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = spacing1)
                ) {
                    LazyColumn() {
                        items(cartItems.size) { index ->
                            val item = cartItems[index]
                            val product = products[item.productSlug]
                            CartItem(
                                cartItem = item,
                                product = product,
                                painter = itemPainter(item),
                                onDetailsClick = { onProductClick(it) },
                                onDecrementCartItem = { onDecrementCartItem(item) },
                                onIncrementCartItem = { onIncrementCartItem(item) },
                                onRemoveCartItem = { onRemoveCartItem(item) },
                                onVariantChange = { variantId ->
                                    if (product != null) {
                                        onVariantChange(item, product, variantId)
                                    }
                                })
                            Spacer(modifier = Modifier.height(spacing1))
                        }
                    }
                    PrimaryButton(onclick = { clearCartConfirm = true }) {
                        Text(
                            text = "Clear",
                            color = ColorGrey100,
                            fontSize = fontSize5,
                            fontFamily = Outfit,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                if (clearCartConfirm) {
                    ShopreeAlertDialog(
                        title = "Clear Cart",
                        text = "Are you sure you want to clear your cart?",
                        icon = Icons.Outlined.Cart,
                        onDissmissRequest = { clearCartConfirm = false },
                        onConfirmation = {
                            onClearCart()
                            clearCartConfirm = false
                        })
                }
            }

            is AsyncState.Error -> {}
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing3),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = spacing3),
                text = "%.0f$".format(price),
                fontSize = fontSize6,
                fontWeight = FontWeight.Bold,
                fontFamily = Outfit
            )
            PrimaryButton(onclick = { onCheckout() }) {
                Text(
                    text = "Checkout",
                    color = ColorGrey100,
                    fontSize = fontSize6,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Outfit
                )
            }
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
        ), CartItem(
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
        ), "product-2" to Product(
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
            state = CartUiState(cartState = AsyncState.Success(CartData(items = sampleCartItems, products = sampleProducts))),
            price = 45.0,
            onBack = {},
            onCheckout = {},
            onDecrementCartItem = {},
            onIncrementCartItem = {},
            onRemoveCartItem = {},
            onClearCart = {},
            onProductClick = {},
            onVariantChange = { _, _, _ -> },
            itemPainter = { item ->
                if (item.productSlug == "product-1") {
                    painterResource(CoreUiR.drawable.demo)
                } else {
                    painterResource(CoreUiR.drawable.large_image_demo)
                }
            })
    }
}
