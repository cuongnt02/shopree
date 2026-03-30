package com.ntc.shopree.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.Neutral100
import com.ntc.shopree.core.ui.theme.Neutral200
import com.ntc.shopree.core.ui.theme.Neutral400
import com.ntc.shopree.core.ui.theme.Neutral50
import com.ntc.shopree.core.ui.theme.Neutral500
import com.ntc.shopree.core.ui.theme.Neutral600
import com.ntc.shopree.core.ui.theme.Neutral900
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize1
import com.ntc.shopree.core.ui.theme.fontSize2
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.fontSize5
import com.ntc.shopree.core.ui.theme.radius3
import com.ntc.shopree.core.ui.theme.radius5
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.theme.spacing5
import com.ntc.shopree.core.ui.theme.spacing6
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreen : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ProfileEvent.NavigateBack -> onBack()
            is ProfileEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    Scaffold(
        containerColor = Neutral50,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Neutral50
                ),
                title = {
                    Text(
                        text = "Shopree",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = fontSize3,
                        color = Neutral900
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "navigate back",
                        tint = Neutral900,
                        modifier = Modifier
                            .clickable { onBack() }
                            .size(32.dp)
                            .padding(start = spacing2)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = spacing3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(spacing6))

            // Avatar
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Neutral200),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "avatar placeholder",
                    modifier = Modifier.size(44.dp),
                    tint = Neutral500
                )
            }

            Spacer(Modifier.height(spacing3))

            Text(
                text = uiState.user?.name ?: "—",
                fontFamily = Outfit,
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize5,
                color = Neutral900
            )

            Spacer(Modifier.height(spacing1))

            Text(
                text = uiState.user?.email ?: "—",
                fontFamily = Outfit,
                fontWeight = FontWeight.Normal,
                fontSize = fontSize2,
                color = Neutral500
            )

            Spacer(Modifier.height(spacing2))

            // Role chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(radius5))
                    .background(Neutral200)
                    .padding(horizontal = spacing2, vertical = 4.dp)
            ) {
                Text(
                    text = uiState.user?.role ?: "BUYER",
                    fontFamily = Outfit,
                    fontWeight = FontWeight.Medium,
                    fontSize = fontSize1,
                    color = Neutral600
                )
            }

            Spacer(Modifier.height(spacing6))

            // Personal info card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(radius3))
                    .background(Neutral100)
            ) {
                ProfileInfoRow(label = "Phone", value = uiState.user?.phone ?: "—")
                HorizontalDivider(color = Neutral200, thickness = 0.5.dp)
                ProfileInfoRow(label = "Email", value = uiState.user?.email ?: "—")
            }

            Spacer(Modifier.height(spacing5))

            // Account section header
            Text(
                text = "Account",
                fontFamily = Outfit,
                fontWeight = FontWeight.Medium,
                fontSize = fontSize2,
                color = Neutral500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing2)
            )

            // Account actions card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(radius3))
                    .background(Neutral100)
            ) {
                ProfileActionRow(label = "Edit Profile") { /* TODO */ }
                HorizontalDivider(color = Neutral200, thickness = 0.5.dp)
                ProfileActionRow(label = "Change Password") { /* TODO */ }
            }

            Spacer(Modifier.height(spacing6))
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing3, vertical = spacing3),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = Outfit,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize2,
            color = Neutral500
        )
        Text(
            text = value,
            fontFamily = Outfit,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize2,
            color = Neutral900
        )
    }
}

@Composable
private fun ProfileActionRow(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = spacing3, vertical = spacing3),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = Outfit,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize2,
            color = Neutral900
        )
        Text(
            text = "›",
            fontFamily = Outfit,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize3,
            color = Neutral400
        )
    }
}
