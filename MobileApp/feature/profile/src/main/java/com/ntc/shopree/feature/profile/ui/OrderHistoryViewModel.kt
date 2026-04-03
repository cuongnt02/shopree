package com.ntc.shopree.feature.profile.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.profile.domain.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ShopreeViewModel<OrderHistoryUiState, OrderHistoryEvent>(OrderHistoryUiState()) {

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getOrdersUseCase()
                .onSuccess { orders ->
                    _uiState.update { it.copy(isLoading = false, orders = orders) }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    emitEvent(OrderHistoryEvent.ShowSnackbar("Failed to load orders"))
                }
        }
    }
}
