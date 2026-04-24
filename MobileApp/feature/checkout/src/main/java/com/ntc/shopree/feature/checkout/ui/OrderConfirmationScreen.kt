package com.ntc.shopree.feature.checkout.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.utils.formatVnd
import kotlinx.serialization.Serializable

@Serializable
data class OrderConfirmationScreen(
    val orderNumber: String,
    val totalCents: Long
) : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(
    orderNumber: String,
    totalCents: Long,
    onBackHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.CheckCircle,
            contentDescription = "order confirmation icon",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Order Placed!")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = orderNumber, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.width(4.dp))
        Text("Total: ${formatVnd(totalCents)}", style = MaterialTheme.typography.bodyLarge)
        Text("Pay cash when you pick up your order", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(onclick = { onBackHome() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Back to Home")
        }
    }
}