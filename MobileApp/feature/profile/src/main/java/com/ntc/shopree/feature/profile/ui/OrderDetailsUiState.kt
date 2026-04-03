package com.ntc.shopree.feature.profile.ui

import com.ntc.shopree.core.model.dto.OrderResponse

data class OrderDetailsUiState(
    val isLoading: Boolean = false,
    val order: OrderResponse? = null
)

sealed interface OrderDetailsEvent {
    data class ShowSnackbar(val message: String) : OrderDetailsEvent
}
