package com.ntc.shopree.feature.catalog.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.ntc.shopree.core.model.AsyncState
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.ProductVariant
import com.ntc.shopree.core.ui.components.Badge
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.core.ui.theme.MobileAppTheme
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.Red500
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.fontSize4
import com.ntc.shopree.core.ui.theme.fontSize5
import com.ntc.shopree.core.ui.theme.fontSize6
import com.ntc.shopree.core.ui.theme.fontSize7
import com.ntc.shopree.core.ui.theme.radius1
import com.ntc.shopree.core.ui.theme.radius2
import com.ntc.shopree.core.ui.theme.radius4
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.core.ui.utils.formatVnd
import com.ntc.shopree.feature.cart.ui.CartButton
import com.ntc.shopree.feature.cart.ui.CartViewModel
import kotlinx.serialization.Serializable
import com.ntc.shopree.core.ui.R as CoreUiR

@Serializable
data class ProductDetails(val slug: String) : NavKey

@Composable
fun ProductDetailsScreen(
    navKey: ProductDetails,
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    onCart: () -> Unit
) {
    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val state by productDetailsViewModel.uiState.collectAsStateWithLifecycle()
    val cartQuantity by cartViewModel.quantity.collectAsStateWithLifecycle()

    LaunchedEffect(productDetailsViewModel) {
        productDetailsViewModel.loadProductDetails(navKey.slug)
    }

    ObserveAsEvents(productDetailsViewModel.events) { event ->
        when (event) {
            is ProductDetailsEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    ProductDetailsScreenContent(
        state = state,
        isAddingToCart = state.isAddingToCart,
        cartQuantity = cartQuantity,
        onBack = onBack,
        onCheckout = onCheckout,
        onCart = onCart,
        onVariantSelected = { productDetailsViewModel.selectVariant(it) },
        onAddToCart = {
            val productState = state.productState
            if (productState is AsyncState.Success) {
                productDetailsViewModel.addProductToCart(productState.data, state.selectedVariant)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreenContent(
    state: ProductDetailsUiState,
    isAddingToCart: Boolean,
    cartQuantity: Int = 0,
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    onCart: () -> Unit,
    onVariantSelected: (ProductVariant) -> Unit,
    onAddToCart: () -> Unit,
    imagePainter: Painter? = null
) {
    var productLiked by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState())
        ) {
            CenterAlignedTopAppBar(title = { Text(text = "Details") }, navigationIcon = {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "arrow back icon",
                    modifier = Modifier.clickable { onBack() })
            }, actions = {
                Icon(
                    imageVector = if (productLiked) Icons.Filled.Heart else Icons.Outlined.Heart,
                    contentDescription = "heart icon",
                    modifier = Modifier.clickable { productLiked = !productLiked })
                CartButton(quantity = cartQuantity, onNavigate = onCart)
            })
            ProductImage(
                state = state, modifier = Modifier.fillMaxWidth(), painter = imagePainter
            )
            ProductDescription(
                state = state, onVariantSelected = onVariantSelected, imagePainter = imagePainter
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
        Row(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(containerColor = ColorGrey700),
                onClick = onAddToCart,
                shape = RoundedCornerShape(radius1),
                modifier = Modifier.size(48.dp)
            ) {
                if (isAddingToCart) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        imageVector = Icons.Outlined.CartAdd,
                        contentDescription = "add to cart icon",
                        tint = ColorGrey100,
                    )
                }
            }
            Spacer(Modifier.width(spacing1))
            PrimaryButton(onclick = onCheckout, text = "Check out", modifier = Modifier.height(48.dp)) {
                Text(text = "Check out", fontSize = fontSize4, fontFamily = Outfit, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun ProductImage(
    state: ProductDetailsUiState,
    modifier: Modifier = Modifier,
    painter: Painter? = null
) {
    val imageModifier = modifier
        .padding(horizontal = spacing3)
        .clip(RoundedCornerShape(radius4))

    when (val productState = state.productState) {
        is AsyncState.Loading -> {
            CircularProgressIndicator()
        }

        is AsyncState.Success -> {
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = "product image",
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = productState.data.mainImage,
                    contentDescription = "product image",
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop
                )
            }
        }

        is AsyncState.Error -> {
            Log.e("com.ntc.shopree.ProductDetails", "ProductImage: ${productState.message}")
            Text(text = productState.message, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun ProductDescription(
    state: ProductDetailsUiState,
    onVariantSelected: (ProductVariant) -> Unit,
    modifier: Modifier = Modifier,
    imagePainter: Painter? = null
) {
    when (val productState = state.productState) {
        is AsyncState.Loading -> {
            CircularProgressIndicator()
        }

        is AsyncState.Success -> {
            val product = productState.data
            val selectedVariant = state.selectedVariant
            Column(modifier = modifier.padding(spacing3)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Description",
                        fontSize = fontSize3,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Outfit
                    )
                    Text(
                        text = formatVnd(selectedVariant?.price ?: product.price),
                        fontSize = fontSize6,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Outfit
                    )
                }
                Spacer(modifier = Modifier.height(spacing1))
                Text(
                    text = product.title,
                    fontSize = fontSize7,
                    fontFamily = Outfit,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = product.description,
                    textAlign = TextAlign.Justify,
                    fontSize = fontSize3,
                    fontFamily = Outfit
                )
                if (product.variants.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(spacing3))
                    Text(text = "Variants", fontSize = fontSize3, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(spacing1))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(spacing2)) {
                        items(product.variants) { variant ->
                            val isSelected = variant.id == selectedVariant?.id
                            Column(modifier = Modifier
                                .clickable { onVariantSelected(variant) }
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) ColorGrey700 else ColorGrey400,
                                    shape = RoundedCornerShape(radius2)
                                )
                                .clip(RoundedCornerShape(radius2))
                                .padding(spacing1),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(spacing1)) {
                                if (imagePainter != null) {
                                    Image(
                                        painter = imagePainter,
                                        contentDescription = variant.title,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(radius2)),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    AsyncImage(
                                        model = product.mainImage,
                                        contentDescription = variant.title,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(radius2)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Badge(
                                    text = variant.title ?: "Default",
                                    color = if (isSelected) ColorGrey700 else ColorGrey400
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(spacing3))
            }
        }

        is AsyncState.Error -> {
            Text(text = productState.message, color = Red500)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailsScreenPreview() {
    val variants = listOf(
        ProductVariant("v1", "Small", "sku1", 10000, null, 10),
        ProductVariant("v2", "Medium", "sku2", 15000, null, 5),
        ProductVariant("v3", "Large", "sku3", 20000, null, 3)
    )
    val product = Product(
        id = "p1",
        title = "Sample Hoodie",
        slug = "sample-hoodie",
        vendorName = "Vendor 1",
        description = "A comfortable premium hoodie suitable for all seasons. Made from 100% organic cotton with a relaxed fit and soft inner lining.",
        mainImage = "",
        variants = variants
    )
    MobileAppTheme {
        ProductDetailsScreenContent(
            state = ProductDetailsUiState(
                productState = AsyncState.Success(product),
                selectedVariant = variants[0]
            ),
            isAddingToCart = false,
            onBack = {},
            onCheckout = {},
            onCart = {},
            onVariantSelected = {},
            onAddToCart = {},
            imagePainter = painterResource(CoreUiR.drawable.large_image_demo)
        )
    }
}
