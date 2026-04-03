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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.model.User
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.TextInput
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
data class EditProfileScreen(val user: User) : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    user: User,
    onBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val nameState = remember { TextFieldState(initialText = user.name) }
    val phoneState = remember { TextFieldState(initialText = user.phone) }

    val onSubmit: () -> Unit = {
        val newName = nameState.text.toString()
        val newPhone = phoneState.text.toString()
        if (newName == user.name && newPhone == user.phone) {
            viewModel.emitNoChanges()
        } else {
            viewModel.showConfirmDialog()
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is EditProfileEvent.NavigateBack -> onBack()
            is EditProfileEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    if (uiState.showConfirmDialog) {
        AlertDialog(
            containerColor = Neutral50,
            onDismissRequest = { viewModel.dismissConfirmDialog() },
            title = {
                Text(
                    text = "Update Profile",
                    fontFamily = Outfit,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize3,
                    color = Neutral900
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to save these changes?",
                    fontFamily = Outfit,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize2,
                    color = Neutral600
                )
            },
            confirmButton = {
                PrimaryButton(
                    onclick = {
                        viewModel.updateProfile(
                            nameState.text.toString(),
                            phoneState.text.toString()
                        )
                    },
                    text = "Update",
                    fontSize = fontSize2
                )
            },
            dismissButton = {
                Box(
                    modifier = Modifier
                        .clickable { viewModel.dismissConfirmDialog() }
                        .padding(horizontal = spacing2, vertical = spacing1)
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Medium,
                        fontSize = fontSize2,
                        color = Neutral600
                    )
                }
            }
        )
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
                        text = if (uiState.isEditMode) "Edit Profile" else "Shopree",
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
                            .clickable {
                                if (uiState.isEditMode) viewModel.exitEditMode()
                                else onBack()
                            }
                            .size(32.dp)
                            .padding(start = spacing2)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (uiState.isEditMode) onSubmit()
                            else viewModel.enterEditMode()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = if (uiState.isEditMode) "Save changes" else "Edit profile",
                            tint = if (uiState.isEditMode) Neutral900 else Neutral400
                        )
                    }
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
                text = user.name,
                fontFamily = Outfit,
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize5,
                color = Neutral900
            )

            Spacer(Modifier.height(spacing1))

            Text(
                text = user.email,
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
                    text = user.role,
                    fontFamily = Outfit,
                    fontWeight = FontWeight.Medium,
                    fontSize = fontSize1,
                    color = Neutral600
                )
            }

            Spacer(Modifier.height(spacing6))

            if (!uiState.isEditMode) {
                // Read mode: static info card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(radius3))
                        .background(Neutral100)
                ) {
                    EditProfileInfoRow(label = "Phone", value = user.phone)
                    HorizontalDivider(color = Neutral200, thickness = 0.5.dp)
                    EditProfileInfoRow(label = "Email", value = user.email)
                }
            } else {
                // Edit mode: input fields
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Name",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Medium,
                        fontSize = fontSize2,
                        color = Neutral600
                    )
                    Spacer(Modifier.height(spacing1))
                    TextInput(
                        state = nameState,
                        placeholder = "Your name",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(spacing3))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Phone",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.Medium,
                        fontSize = fontSize2,
                        color = Neutral600
                    )
                    Spacer(Modifier.height(spacing1))
                    TextInput(
                        state = phoneState,
                        placeholder = "Your phone number",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(spacing5))

                PrimaryButton(
                    onclick = onSubmit,
                    modifier = Modifier.fillMaxWidth(),
                    text = "Update Profile",
                    fontSize = fontSize3,
                    loading = uiState.isSaving
                )
            }

            Spacer(Modifier.height(spacing6))
        }
    }
}

@Composable
private fun EditProfileInfoRow(label: String, value: String) {
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
