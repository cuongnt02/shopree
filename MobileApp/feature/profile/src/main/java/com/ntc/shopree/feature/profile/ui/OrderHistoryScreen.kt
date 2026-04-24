package com.ntc.shopree.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.model.dto.OrderSummaryResponse
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.Neutral100
import com.ntc.shopree.core.ui.theme.Neutral200
import com.ntc.shopree.core.ui.theme.Neutral400
import com.ntc.shopree.core.ui.theme.Neutral50
import com.ntc.shopree.core.ui.theme.Neutral500
import com.ntc.shopree.core.ui.theme.Neutral600
import com.ntc.shopree.core.ui.theme.Neutral700
import com.ntc.shopree.core.ui.theme.Neutral800
import com.ntc.shopree.core.ui.theme.Neutral900
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.Red500
import com.ntc.shopree.core.ui.theme.fontSize1
import com.ntc.shopree.core.ui.theme.fontSize2
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.radius3
import com.ntc.shopree.core.ui.theme.radius5
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.core.ui.utils.formatVnd
import kotlinx.serialization.Serializable

@Serializable
data object OrderHistoryScreen : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    onBack: () -> Unit,
    onOrderClick: (String) -> Unit,
    viewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is OrderHistoryEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    Scaffold(
        containerColor = Neutral50,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Neutral50),
                title = {
                    Text(
                        text = "Shopree",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = fontSize3,
                        color = Neutral900
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "navigate back",
                        tint = Neutral900,
                        modifier = Modifier
                            .clickable { onBack() }
                            .size(32.dp)
                            .padding(start = spacing2)
                    )
                }
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Neutral700)
                }
            }
            uiState.orders.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No orders yet",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize2,
                        color = Neutral400
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = spacing3),
                    verticalArrangement = Arrangement.spacedBy(spacing2)
                ) {
                    item { Spacer(Modifier.height(spacing3)) }
                    items(uiState.orders) { order ->
                        OrderSummaryCard(order = order, onClick = { onOrderClick(order.id) })
                    }
                    item { Spacer(Modifier.height(spacing3)) }
                }
            }
        }
    }
}

@Composable
private fun OrderSummaryCard(order: OrderSummaryResponse, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(radius3))
            .background(Neutral100)
            .clickable(onClick = onClick)
            .padding(spacing3),
        verticalArrangement = Arrangement.spacedBy(spacing1)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = order.orderNumber,
                fontFamily = Outfit,
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize2,
                color = Neutral900
            )
            OrderStatusChip(status = order.status)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${order.itemCount} item${if (order.itemCount != 1) "s" else ""}",
                fontFamily = Outfit,
                fontWeight = FontWeight.Normal,
                fontSize = fontSize1,
                color = Neutral500
            )
            Text(
                text = formatVnd(order.totalCents),
                fontFamily = Outfit,
                fontWeight = FontWeight.Medium,
                fontSize = fontSize2,
                color = Neutral800
            )
        }
        Text(
            text = formatOrderDate(order.placedAt),
            fontFamily = Outfit,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize1,
            color = Neutral400
        )
    }
}

@Composable
private fun OrderStatusChip(status: String) {
    val isNegative = status.uppercase() in setOf("DECLINED", "CANCELLED")
    val bgColor = if (isNegative) Red500.copy(alpha = 0.1f) else Neutral200
    val textColor = if (isNegative) Red500 else Neutral600
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(radius5))
            .background(bgColor)
            .padding(horizontal = spacing2, vertical = 2.dp)
    ) {
        Text(
            text = status,
            fontFamily = Outfit,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize1,
            color = textColor
        )
    }
}

private fun formatOrderDate(isoDate: String): String {
    return try {
        try {
            val date = java.time.OffsetDateTime.parse(isoDate)
            date.format(java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy"))
        } catch (e: Exception) {
            val date = java.time.LocalDateTime.parse(isoDate)
            date.format(java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy"))
        }
    } catch (e: Exception) {
        isoDate.take(10)
    }
}
