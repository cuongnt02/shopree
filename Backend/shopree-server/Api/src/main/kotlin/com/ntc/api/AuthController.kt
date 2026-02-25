package com.ntc.api

import com.ntc.api.payload.request.LoginRequest
import com.ntc.api.payload.request.RefreshTokenRequest
import com.ntc.domain.exception.AuthenticationException
import com.ntc.service.dto.LoginResponse
import com.ntc.service.AuthService
import com.ntc.service.dto.RefreshTokenResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login") // TODO: Validation on each request body
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(authService.login(username = loginRequest.email, password = loginRequest.password))
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<RefreshTokenResponse> {
        try {
            val response = authService.refreshAccessToken(request.refreshToken)
            return ResponseEntity.ok(response)
        } catch (e: AuthenticationException) {
            return ResponseEntity<RefreshTokenResponse>.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("logout")
    fun logout(@RequestBody request: RefreshTokenRequest): ResponseEntity<Void> {
        authService.logout(request.refreshToken)
        return ResponseEntity.noContent().build()
    }
}
