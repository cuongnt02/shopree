package com.ntc.shopree.feature.checkout.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.icons.Icons
import kotlinx.serialization.Serializable

@Serializable
data class OrderConfirmationScreen(val orderNumber: String, val totalCents: Long): NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(orderNumber: String, totalCents: Long, onBackHome: () -> Unit) {

}