package com.ntc.shopree.feature.profile.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.profile.domain.GetOrderDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase
) : ShopreeViewModel<OrderDetailsUiState, OrderDetailsEvent>(OrderDetailsUiState()) {

    fun loadOrder(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getOrderDetailsUseCase(id)
                .onSuccess { order ->
                    _uiState.update { it.copy(isLoading = false, order = order) }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    emitEvent(OrderDetailsEvent.ShowSnackbar("Failed to load order"))
                }
        }
    }
}
