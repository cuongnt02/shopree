package com.ntc.shopree.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ntc.shopree.core.ui.R
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize2
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.radius1
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2

@Composable
fun ProductCard(
    image: @Composable () -> Unit,
    name: String,
    price: String,
    onClick: () -> Unit
) {
    return Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = ColorGrey100),
        modifier = Modifier
            .width(173.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(4.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Heart, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(14.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .widthIn(max = 140.dp)
            ) {
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
fun ProductCard(
    imageSource: String,
    name: String,
    price: String,
    onClick: () -> Unit
) {
    ProductCard(
        onClick = onClick, image = {
            AsyncImage(model = imageSource, contentDescription = "product $name image")
        }, name = name, price = price
    )
}

@Composable
fun ProductCardV2(
    image: @Composable () -> Unit,
    name: String,
    price: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = ColorGrey100),
        modifier = Modifier
            .width(173.dp)
            .wrapContentHeight().border(1.dp, ColorGrey400, RoundedCornerShape(radius1)),
        shape = RoundedCornerShape(spacing1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing1)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(spacing1)
            ) {
                Icon(imageVector = Icons.Outlined.Heart, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(spacing2))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .widthIn(max = 140.dp)
                    .fillMaxWidth()
            ) {
                image()
            }
            Spacer(modifier = Modifier.height(spacing2))
            Text(
                text = name,
                fontSize = fontSize3,
                fontFamily = Outfit,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(spacing1))
            Text(
                text = price,
                fontSize = fontSize2,
                fontFamily = Outfit,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(spacing1))
        }
    }
}

@Composable
fun ProductCardV2(
    imageSource: String,
    name: String,
    price: String,
    onClick: () -> Unit
) {
    ProductCardV2(
        onClick = onClick, image = {
            AsyncImage(model = imageSource, contentDescription = "product $name image")
        }, name = name, price = price
    )
}

@Composable
@Preview
fun ProductCardPreview() {
    ProductCardV2(image = {
        Image(
            painterResource(R.drawable.demo),
            contentDescription = null,
            Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }, name = "Demo Product", price = "$100.00", onClick = {})
}

@Composable
@Preview
fun ProductCardLargeImagePreview() {
    ProductCardV2(image = {
        Image(
            painterResource(R.drawable.large_image_demo),
            contentDescription = null,
            Modifier.fillMaxWidth()
        )
    }, name = "Demo Product", price = "$100.00", onClick = {})
}


