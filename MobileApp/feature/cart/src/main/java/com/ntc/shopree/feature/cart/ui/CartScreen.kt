package com.ntc.shopree.feature.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LazyColumn(modifier = modifier) {
                        items(cartItems.size) { cartItem ->
                            CartItem(cartItem = cartItems[cartItem], onDetailsClick = {
                                // TODO: Navigate to product details
                            })
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
                        }
                    )
                }


            }

            is CartUiState.Error -> {
                val message = (state as CartUiState.Error).message
                LaunchedEffect(message) {
                    SnackbarController.sendEvent(SnackbarEvent(message = message))
                }
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Total: $price")
            PrimaryButton(onclick = {}) {
                Text(text = "Checkout", style = MaterialTheme.typography.labelMedium)
            }
        }
    }


}

@Composable
fun CartItem(cartItem: CartItem, modifier: Modifier = Modifier, onDetailsClick: (String) -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ColorGrey200)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = cartItem.imageUrl,
                contentDescription = "product ${cartItem.productSlug} image",
                modifier = Modifier.background(color = ColorGrey200, shape = CircleShape)
            )
            Column {
                Text(text = cartItem.productName)
                Text(
                    text = "View details", modifier = Modifier.clickable(
                        onClick = {
                            onDetailsClick(cartItem.productSlug)
                        })
                )
                Text(text = cartItem.price.toString())
            }
            Column(verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add quantity")
                Text(text = cartItem.quantity.toString())
                Icon(imageVector = Icons.Filled.Remove, contentDescription = "Remove quantity")
            }
        }
    }
}