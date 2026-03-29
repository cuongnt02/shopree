package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ntc.shopree.core.model.Category
import com.ntc.shopree.core.ui.components.Badge
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.core.ui.theme.ColorPrimary100
import com.ntc.shopree.core.ui.theme.spacing1

@Composable
fun Categories(
    categories: List<Category>,
    selectedCategory: Category? = null,
    onCategoryClick: (Category) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyRow(contentPadding = PaddingValues(vertical = spacing1)) {
        items(categories) { category ->
            val isSelected = category.slug == selectedCategory?.slug
            Badge(
                text = category.name,
                modifier = Modifier.height(28.dp),
                color = if (isSelected) ColorGrey700 else ColorGrey500,
                onClick = { onCategoryClick(category) },
                enabled = true
            )
            Spacer(Modifier.width(spacing1))
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun CategoriesPreview() {
    val sampleCategories = listOf(
        Category("1", "Popular", "popular"),
        Category("2", "Clothing", "clothing"),
        Category("3", "Shoes", "shoes"),
        Category("4", "Accessories", "accessories"),
        Category("5", "Bags", "bags")
    )
    Categories(
        categories = sampleCategories,
        selectedCategory = sampleCategories[0],
        modifier = Modifier.background(ColorPrimary100)
    )
}
