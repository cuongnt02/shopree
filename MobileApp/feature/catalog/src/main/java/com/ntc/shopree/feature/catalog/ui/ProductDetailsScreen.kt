package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.icons.Icons
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetails(val slug: String) : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(navKey: ProductDetails, onBack: () -> Unit) {
    var productLiked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(title = { Text(text = "Details") }, navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "arrow back icon",
                modifier = Modifier.clickable {
                    onBack()
                })
        }, actions = {
            Icon(
                imageVector = if (productLiked) Icons.Filled.Heart else Icons.Outlined.Heart, contentDescription = "heart icon",
                modifier = Modifier.clickable {
                    productLiked = !productLiked
                }
            )
        }
)
    }
}