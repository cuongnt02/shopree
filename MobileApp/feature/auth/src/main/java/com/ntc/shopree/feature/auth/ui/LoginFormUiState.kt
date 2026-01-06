package com.ntc.shopree.feature.auth.ui

data class LoginFormUiState(
    val emailOrPhone: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val showPassword: Boolean = false,
)

data class LoginFormErrors(
    val blankEmailOrPhoneError: String? = null,
    val passwordError: String? = null,
    val invalidEmailFormatError: String? = null,
) {
    val isValid: Boolean
        get() = blankEmailOrPhoneError == null && passwordError == null && invalidEmailFormatError == null
}


