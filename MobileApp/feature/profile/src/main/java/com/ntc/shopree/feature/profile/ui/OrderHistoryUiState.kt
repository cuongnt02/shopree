package com.ntc.shopree.feature.profile.ui

import com.ntc.shopree.core.model.dto.OrderSummaryResponse

data class OrderHistoryUiState(
    val isLoading: Boolean = false,
    val orders: List<OrderSummaryResponse> = emptyList()
)

sealed interface OrderHistoryEvent {
    data class ShowSnackbar(val message: String) : OrderHistoryEvent
}
