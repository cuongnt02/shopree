package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ntc.shopree.core.ui.components.ShimmerBox
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2

@Composable
fun ProductCardSkeleton() {
    Card(
        colors = CardDefaults.cardColors(containerColor = ColorGrey200),
        modifier = Modifier
            .width(173.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(spacing1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing1)
        ) {
            // Favorite button placeholder
            ShimmerBox(
                modifier = Modifier
                    .align(Alignment.End)
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(spacing2))
            // Image placeholder
            ShimmerBox(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(spacing1))
            )
            Spacer(modifier = Modifier.height(spacing2))
            // Product name placeholder
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(spacing1))
            // Price placeholder
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(spacing1))
        }
    }
}

@Composable
fun ProductSectionSkeleton(count: Int) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(spacing2),
        verticalArrangement = Arrangement.spacedBy(spacing2),
        contentPadding = PaddingValues(horizontal = spacing2)
    ) {
        items(count) {
            ProductCardSkeleton()
        }
    }
}

@Composable
fun SearchSectionSkeleton() {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = spacing2)) {
        ShimmerBox(modifier = Modifier.weight(1f).height(56.dp).clip(RoundedCornerShape(28.dp)))
        Spacer(modifier = Modifier.width(spacing1))
        ShimmerBox(modifier = Modifier.size(56.dp).clip(CircleShape))
    }
}

@Composable
fun CategorySectionSkeleton() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing1), contentPadding = PaddingValues(horizontal = spacing2)) {
        items(5) {
            ShimmerBox(modifier = Modifier.width(80.dp).height(32.dp))
        }
    }
}