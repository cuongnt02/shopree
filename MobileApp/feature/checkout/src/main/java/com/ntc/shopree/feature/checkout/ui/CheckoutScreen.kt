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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.icons.Icons
import kotlinx.serialization.Serializable

@Serializable
data object CheckoutScreen : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onOrderPlaced: (orderNumber: String, totalCents: Long) -> Unit
) {
    val viewModel: CheckoutViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is CheckoutUiState.Success) {
            val order = (uiState as CheckoutUiState.Success).order
            onOrderPlaced(order.orderNumber, order.totalCents)
        }
    }

    Column {
        CenterAlignedTopAppBar(title = { Text(text = "Checkout") }, navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "back icon",
                modifier = Modifier.clickable {
                    onBack()
                })
        })
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { item ->
                OrderItem(
                    productName = item.productName,
                    quantity = item.quantity,
                    price = item.price
                )
            }
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Payment", style = MaterialTheme.typography.bodyMedium)
            Text("Cash on Pickup", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            onclick = {viewModel.placeOrder()},
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is CheckoutUiState.Loading
        ) {
            if (uiState is CheckoutUiState.Loading) {
                CircularProgressIndicator()
            } else {
                Text(text = "Place Order")
            }
        }
    }
}

@Composable
fun OrderItem(productName: String, quantity: Int, price: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = productName, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Item $quantity", style = MaterialTheme.typography.bodySmall)
        }
        // TODO: Currency localization ?
        Text(text = "${price * quantity} VND")
    }
    HorizontalDivider()
}