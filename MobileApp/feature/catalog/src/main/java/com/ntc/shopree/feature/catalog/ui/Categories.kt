package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorPrimary100

@Composable
fun Categories(categories: List<String>, modifier: Modifier = Modifier) {
    LazyRow {
        items(categories) { category ->
            CategoryItem(item = category)
        }
    }
}

@Composable
fun CategoryItem(item: String) {
    Box(
        modifier = Modifier.background(
            color = ColorGrey500, shape = RoundedCornerShape(40.dp)
        )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            text = item,
            color = ColorPrimary100,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun CategoriesPreview() {
    Categories(
        listOf("popular", "clothing", "shoes", "accessories", "bags"),
        modifier = Modifier.background(ColorPrimary100)
    )
}


