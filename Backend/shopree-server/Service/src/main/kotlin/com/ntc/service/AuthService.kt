package com.ntc.service

import com.ntc.security.JwtTokenProvider
import com.ntc.service.dto.AuthResult
import com.ntc.shopree.model.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthService(private val authenticationManager: AuthenticationManager, private val tokenProvider: JwtTokenProvider) {
    fun login(username: String, password: String): AuthResult {
        try {
            val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

            SecurityContextHolder.getContext().authentication = auth
            val user = auth.principal as User
            val jwt = tokenProvider.generateToken(auth.principal as User)
            val payload = tokenProvider.getJwtPayload(jwt)
            println("SERVICE: User authenticated successfully: ${auth.principal}")
            println("SERVICE: User authorities: ${auth.authorities}")
            return AuthResult.Success(
                accessToken = jwt,
                expiresAt = payload.expiration.time,
                userId = payload.subject,
                role = user.role.toString(),
            )
        } catch (e: Exception) {
            return AuthResult.Error("An unexecpted error occurred.")
        }

    }
}
