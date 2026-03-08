package com.ntc.shopree.feature.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.ShopreeAlertDialog
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.serialization.Serializable
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import com.ntc.shopree.core.model.Product


@Serializable
data object CartScreen : NavKey



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(modifier: Modifier = Modifier, onBack: () -> Unit, onCheckout: () -> Unit) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val state by cartViewModel.uiState.collectAsState()
    val price by cartViewModel.totalPrice.collectAsState()
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
                val cartState = state as CartUiState.Success
                val cartItems = cartState.cartItems
                val products = cartState.products
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LazyColumn(modifier = modifier.weight(1f)) {
                        items(cartItems.size) { index ->
                            val item = cartItems[index]
                            val product = products[item.productSlug]
                            CartItem(
                                cartItem = item,
                                product = product,
                                onDetailsClick = {
                                    // TODO: Navigate to product details
                                },
                                onDecrementCartItem = {
                                    cartViewModel.decrementCartItem(item)
                                },
                                onIncrementCartItem = {
                                    cartViewModel.incrementCartItem(item)
                                },
                                onRemoveCartItem = {
                                    cartViewModel.removeCartItem(item)
                                },
                                onVariantChange = { variantId ->
                                    if (product != null) {
                                        cartViewModel.updateVariant(item, product, variantId)
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
                            cartViewModel.clearCart()
                            clearCartConfirm = false
                        })
                }


            }

            is CartUiState.Error -> {
                val message = (state as CartUiState.Error).message
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
                AsyncImage(
                    model = cartItem.mainImage,
                    contentDescription = "product ${cartItem.productSlug} image",
                    modifier = Modifier.background(color = ColorGrey200, shape = CircleShape)
                )
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