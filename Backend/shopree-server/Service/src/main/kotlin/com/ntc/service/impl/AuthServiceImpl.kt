package com.ntc.service.impl

import com.ntc.security.JwtTokenProvider
import com.ntc.service.AuthService
import com.ntc.service.dto.AuthResult
import com.ntc.shopree.model.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(private val authenticationManager: AuthenticationManager, private val tokenProvider: JwtTokenProvider):
    AuthService {
    override fun login(username: String, password: String): AuthResult {
        try {
            val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

            SecurityContextHolder.getContext().authentication = auth
            val user = auth.principal as User
            val jwt = tokenProvider.generateToken(auth.principal as User)
            val payload = tokenProvider.getJwtPayload(jwt)
            return AuthResult.Success(
                accessToken = jwt,
                expiresAt = payload.expiration.time,
                userId = payload.subject,
                role = user.role.toString(),
            )
        } catch (e: Exception) {
            return AuthResult.Error("An unexpected error occurred.")
        }

    }
}
