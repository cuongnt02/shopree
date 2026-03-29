package com.ntc.shopree.feature.checkout.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.MobileAppTheme
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize5
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.serialization.Serializable
import com.ntc.shopree.core.ui.R as CoreUIR

@Serializable
data object CheckoutScreen : NavKey

@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onOrderPlaced: (orderNumber: String, totalCents: Long) -> Unit
) {
    val viewModel: CheckoutViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CheckoutEvent.OrderPlaced -> onOrderPlaced(event.orderNumber, event.totalCents)
            is CheckoutEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    CheckoutScreenContent(
        uiState = uiState,
        cartItems = cartItems,
        onBack = onBack,
        onPlaceOrder = { viewModel.placeOrder() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreenContent(
    uiState: CheckoutUiState,
    cartItems: List<CartItem>,
    onBack: () -> Unit,
    onPlaceOrder: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(title = { Text(text = "Checkout") }, navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "back icon",
                modifier = Modifier.clickable { onBack() })
        })
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { item ->
                OrderItem(
                    productName = item.productName,
                    quantity = item.quantity,
                    price = item.price,
                    productImage = item.mainImage
                )
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Payment", style = MaterialTheme.typography.bodyMedium)
                Text("Cash on Pickup", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(
                onclick = onPlaceOrder,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is CheckoutUiState.Loading
            ) {
                if (uiState is CheckoutUiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = "Place Order",
                        color = ColorGrey100,
                        fontSize = fontSize5,
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutScreenPreview() {
    val sampleCartItems = listOf(
        CartItem(
            productSlug = "product-1",
            vendorName = "Vendor 1",
            variantId = "v1",
            variantName = "Small",
            productName = "Sample Product 1",
            quantity = 2,
            price = 100000.0,
            mainImage = ""
        ), CartItem(
            productSlug = "product-2",
            vendorName = "Vendor 2",
            variantId = "v3",
            variantName = "Default",
            productName = "Sample Product 2",
            quantity = 1,
            price = 250000.0,
            mainImage = ""
        )
    )
    MobileAppTheme {
        CheckoutScreenContent(
            uiState = CheckoutUiState.Idle,
            cartItems = sampleCartItems,
            onBack = {},
            onPlaceOrder = {})
    }
}
