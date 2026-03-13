package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ntc.shopree.core.ui.components.Badge
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorPrimary100
import com.ntc.shopree.core.ui.theme.spacing1

@Composable
fun Categories(categories: List<String>, modifier: Modifier = Modifier) {
    LazyRow(contentPadding = PaddingValues(vertical = spacing1)) {
        items(categories) { category ->
            CategoryItem(item = category)
            Spacer(Modifier.width(spacing1))
        }
    }
}

@Composable
fun CategoryItem(item: String) {
    Badge(text = item, modifier = Modifier.height(28.dp))
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun CategoriesPreview() {
    Categories(
        listOf("popular", "clothing", "shoes", "accessories", "bags"),
        modifier = Modifier.background(ColorPrimary100)
    )
}


