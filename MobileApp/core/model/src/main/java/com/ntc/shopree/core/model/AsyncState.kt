package com.ntc.shopree.core.model

sealed interface AsyncState<out T> {
    data object Loading : AsyncState<Nothing>
    data class Success<out T>(val data: T) : AsyncState<T>
    data class Error(val message: String) : AsyncState<Nothing>
}
