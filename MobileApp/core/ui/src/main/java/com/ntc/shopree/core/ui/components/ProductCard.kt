package com.ntc.shopree.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey200

@Composable
fun ProductCard(image: @Composable () -> Unit, name: String, price: String) {
    return Card(
        colors = CardDefaults.cardColors(containerColor = ColorGrey200),
        modifier = Modifier
            .width(173.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(ColorGrey100, shape = CircleShape)
                    .padding(4.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Heart, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(14.dp))
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .widthIn(max = 140.dp)) {
                image()
            }
            Spacer(modifier = Modifier.height(19.dp))
            Text(text = name, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = price, style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(11.dp))
        }
    }
}

@Composable
fun ProductCard(imageSource: String, name: String, price: String) {
    ProductCard(
        image = {
            AsyncImage(model = imageSource, contentDescription = "product $name image")
        }, name = name, price = price
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun ProductCardPreview() {
//    val previewHandler: AsyncImagePreviewHandler = AsyncImagePreviewHandler {
//        ColorImage(Color.Red.toArgb(), width = 200, height = 200)
//    }
//    ProductCard(image = {
//        CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
//        AsyncImage(model = "https://s3.ap-southeast-1.amazonaws.com/com.ntc.shopree/Sneakers.png", contentDescription = null)
//    }}, name = "Sneakers", price = "5.99$")
}


