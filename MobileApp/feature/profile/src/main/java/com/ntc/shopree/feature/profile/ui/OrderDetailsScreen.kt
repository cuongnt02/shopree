package com.ntc.shopree.feature.profile.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.model.dto.OrderItemResponse
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
data class OrderDetailsScreen(val orderId: String) : NavKey

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    orderId: String,
    onBack: () -> Unit,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(orderId) {
        viewModel.loadOrder(orderId)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is OrderDetailsEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
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
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Neutral700)
            }
        } else {
            val order = uiState.order
            if (order == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Order not found",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize2,
                        color = Neutral400
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = spacing3),
                    verticalArrangement = Arrangement.spacedBy(spacing3)
                ) {
                    item { Spacer(Modifier.height(spacing1)) }

                    // Order header
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(radius3))
                                .background(Neutral100)
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
                                    fontSize = fontSize3,
                                    color = Neutral900
                                )
                                OrderDetailsStatusChip(status = order.status)
                            }
                            Text(
                                text = formatDetailsDate(order.placedAt),
                                fontFamily = Outfit,
                                fontWeight = FontWeight.Normal,
                                fontSize = fontSize1,
                                color = Neutral400
                            )
                        }
                    }

                    // Items section label
                    item {
                        Text(
                            text = "Items",
                            fontFamily = Outfit,
                            fontWeight = FontWeight.Medium,
                            fontSize = fontSize2,
                            color = Neutral500,
                            modifier = Modifier.padding(bottom = spacing1)
                        )
                    }

                    // Items list
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(radius3))
                                .background(Neutral100)
                        ) {
                            order.items.forEachIndexed { index, item ->
                                OrderItemRow(item = item)
                                if (index < order.items.lastIndex) {
                                    HorizontalDivider(color = Neutral200, thickness = 0.5.dp)
                                }
                            }
                        }
                    }

                    // Total
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(radius3))
                                .background(Neutral100)
                                .padding(spacing3),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                fontFamily = Outfit,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = fontSize2,
                                color = Neutral900
                            )
                            Text(
                                text = formatVnd(order.totalCents),
                                fontFamily = Outfit,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = fontSize2,
                                color = Neutral900
                            )
                        }
                    }

                    item { Spacer(Modifier.height(spacing3)) }
                }
            }
        }
    }
}

@Composable
private fun OrderItemRow(item: OrderItemResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing3, vertical = spacing2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.productTitle,
                fontFamily = Outfit,
                fontWeight = FontWeight.Normal,
                fontSize = fontSize2,
                color = Neutral900
            )
            Text(
                text = "x${item.quantity}",
                fontFamily = Outfit,
                fontWeight = FontWeight.Normal,
                fontSize = fontSize1,
                color = Neutral500
            )
        }
        Text(
            text = formatVnd(item.totalPriceCents),
            fontFamily = Outfit,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize2,
            color = Neutral800
        )
    }
}

@Composable
private fun OrderDetailsStatusChip(status: String) {
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

@RequiresApi(Build.VERSION_CODES.O)
private fun formatDetailsDate(isoDate: String): String {
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
