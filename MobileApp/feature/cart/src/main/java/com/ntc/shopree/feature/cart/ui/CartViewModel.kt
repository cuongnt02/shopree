package com.ntc.shopree.feature.cart.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.AsyncState
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.feature.cart.domain.ClearCartItemUseCase
import com.ntc.shopree.feature.cart.domain.DecrementCartItemUseCase
import com.ntc.shopree.feature.cart.domain.IncrementCartItemUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartQuantityUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartUseCase
import com.ntc.shopree.feature.cart.domain.ObserveTotalPriceUseCase
import com.ntc.shopree.feature.cart.domain.RemoveCartItemUseCase
import com.ntc.shopree.feature.cart.domain.UpdateCartItemUseCase
import com.ntc.shopree.core.network.domain.GetSingleProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartData(
    val items: List<CartItem>,
    val products: Map<String, Product> = emptyMap()
)

data class CartUiState(
    val cartState: AsyncState<CartData> = AsyncState.Loading,
    val isUpdating: Boolean = false
)

sealed interface CartEvent {
    data class ShowSnackbar(val message: String) : CartEvent
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val observeCartUseCase: ObserveCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val observeCartQuantityUseCase: ObserveCartQuantityUseCase,
    private val observeTotalPriceUseCase: ObserveTotalPriceUseCase,
    private val clearCartItemUseCase: ClearCartItemUseCase,
    private val incrementCartItemUseCase: IncrementCartItemUseCase,
    private val decrementCartItemUseCase: DecrementCartItemUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val getSingleProductUseCase: GetSingleProductUseCase
) : ShopreeViewModel<CartUiState, CartEvent>(CartUiState()) {

    // Separate flows used as cart badge count by ProductsScreen and ProductDetailsScreen.
    private val _quantity = MutableStateFlow(0)
    val quantity: StateFlow<Int> = _quantity.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    init {
        observeCart()
        observeCartQuantity()
        observeTotalPrice()
    }

    private fun observeTotalPrice() {
        viewModelScope.launch {
            val result = observeTotalPriceUseCase()
            result.onSuccess { flow ->
                flow.collect { totalPrice -> _totalPrice.value = totalPrice }
            }
            result.onFailure { e ->
                emitEvent(CartEvent.ShowSnackbar(e.message ?: "Unable to observe total price"))
            }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            val result = observeCartUseCase()
            result.onSuccess { flow ->
                flow.collect { cartItems ->
                    val currentProducts = (_uiState.value.cartState as? AsyncState.Success)?.data?.products ?: emptyMap()
                    _uiState.update { it.copy(cartState = AsyncState.Success(CartData(cartItems, currentProducts))) }
                    cartItems.forEach { item ->
                        if (!currentProducts.containsKey(item.productSlug)) {
                            loadProductDetails(item.productSlug)
                        }
                    }
                }
            }
            result.onFailure { e ->
                _uiState.update { it.copy(cartState = AsyncState.Error(e.message ?: "Unable to observe cart items")) }
                emitEvent(CartEvent.ShowSnackbar("Unable to load cart"))
            }
        }
    }

    private fun loadProductDetails(slug: String) {
        viewModelScope.launch {
            val result = getSingleProductUseCase(slug)
            result.onSuccess { product ->
                _uiState.update { state ->
                    val currentData = (state.cartState as? AsyncState.Success)?.data ?: return@update state
                    state.copy(cartState = AsyncState.Success(currentData.copy(
                        products = currentData.products + (slug to product)
                    )))
                }
            }
        }
    }

    private fun observeCartQuantity() {
        viewModelScope.launch {
            val result = observeCartQuantityUseCase()
            result.onSuccess { flow ->
                flow.collect { quantity -> _quantity.value = quantity }
            }
            result.onFailure { e ->
                emitEvent(CartEvent.ShowSnackbar(e.message ?: "Unable to observe cart quantity"))
            }
        }
    }

    fun updateVariant(cartItem: CartItem, product: Product, variantId: String) {
        val variant = product.variants.find { it.id == variantId } ?: return
        val newItem = cartItem.copy(
            variantId = variant.id,
            variantName = variant.title ?: "Default",
            price = variant.price
        )
        viewModelScope.launch {
            val result = updateCartItemUseCase(cartItem, newItem)
            result.onSuccess { emitEvent(CartEvent.ShowSnackbar("Variant updated")) }
            result.onFailure { emitEvent(CartEvent.ShowSnackbar("Failed to update variant")) }
        }
    }

    fun addToCart(cartItem: CartItem) {
        if (_uiState.value.cartState !is AsyncState.Success) return
        _uiState.update { it.copy(isUpdating = true) }
        viewModelScope.launch {
            val result = addToCartUseCase(cartItem)
            result.onSuccess {
                _uiState.update { it.copy(isUpdating = false) }
                emitEvent(CartEvent.ShowSnackbar("Added to cart"))
            }
            result.onFailure {
                _uiState.update { it.copy(isUpdating = false) }
                emitEvent(CartEvent.ShowSnackbar("Failed to add to cart"))
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            val result = clearCartItemUseCase()
            result.onSuccess { emitEvent(CartEvent.ShowSnackbar("Cart cleared")) }
            result.onFailure { emitEvent(CartEvent.ShowSnackbar("Failed to clear cart")) }
        }
    }

    fun incrementCartItem(item: CartItem) {
        viewModelScope.launch {
            val result = incrementCartItemUseCase(item)
            result.onSuccess { emitEvent(CartEvent.ShowSnackbar("Item incremented")) }
            result.onFailure { emitEvent(CartEvent.ShowSnackbar("Failed to increment item")) }
        }
    }

    fun decrementCartItem(item: CartItem) {
        viewModelScope.launch {
            val result = decrementCartItemUseCase(item)
            result.onSuccess { emitEvent(CartEvent.ShowSnackbar("Item decremented")) }
            result.onFailure { emitEvent(CartEvent.ShowSnackbar("Failed to decrement item")) }
        }
    }

    fun removeCartItem(item: CartItem) {
        viewModelScope.launch {
            val result = removeCartItemUseCase(item)
            result.onSuccess { emitEvent(CartEvent.ShowSnackbar("Item removed")) }
            result.onFailure { emitEvent(CartEvent.ShowSnackbar("Failed to remove item")) }
        }
    }
}
