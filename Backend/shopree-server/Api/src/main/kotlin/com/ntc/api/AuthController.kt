package com.ntc.api

import com.ntc.api.payload.LoginRequest
import com.ntc.api.payload.LoginResponse
import com.ntc.api.payload.ResponseBase
import com.ntc.api.payload.error.UnexpectedErrorResponse
import com.ntc.api.payload.toLoginResponse
import com.ntc.service.AuthService
import com.ntc.service.dto.AuthResult
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login") // TODO: Validation on each request body
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<ResponseBase> {
        return when (val result = authService.login(loginRequest.email, loginRequest.password)) {
            is AuthResult.Success -> ResponseEntity<LoginResponse>.ok(result.toLoginResponse() as LoginResponse)
            is AuthResult.Error -> ResponseEntity<LoginResponse>.badRequest().body(result.toLoginResponse() as UnexpectedErrorResponse)
        }
    }
}
