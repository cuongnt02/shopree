package com.ntc.shopree.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// TODO: Change to lazy row
@Composable
fun Categories(items: List<String>) {
    Row() {
        items.forEach { item ->
            CategoryItem(item = item)
        }
    }
}

@Composable
fun CategoryItem(item: String) {
    // TODO: Add category item
}

@Composable
@Preview
fun CategoriesPreview() {
    Categories(listOf("popular", "clothing", "shoes", "accessories", "bags"))
}

