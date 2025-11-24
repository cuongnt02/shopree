package com.ntc.shopree.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.ntc.shopree.data.app.AppContainer
import com.ntc.shopree.ui.theme.ColorGrey300
import com.ntc.shopree.ui.theme.ColorGrey500

@Composable
fun LoginScreen(appContainer: AppContainer) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SubcomposeAsyncImage(model = "https://s3.ap-southeast-1.amazonaws.com/com.ntc.shopree/Login.jpg", contentDescription = "login image", loading = {CircularProgressIndicator()})
        Spacer(Modifier.height(2.dp))
        Text(text = "Welcome", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(2.dp))
        Text(text = "Please login to continue", style = MaterialTheme.typography.labelMedium, color = ColorGrey500, fontWeight = FontWeight.Normal)
        Spacer(Modifier.height(24.dp))


    }
}

@Composable
fun LoginForm() {
    
}