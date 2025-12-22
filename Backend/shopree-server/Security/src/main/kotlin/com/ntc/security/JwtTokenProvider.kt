package com.ntc.security

import com.ntc.shopree.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {
    private val secretKey = Jwts.SIG.HS512.key().build()
    private val tokenValidityInMilliseconds = 864_000_000

    fun generateToken(userDetails: User): String {
        val now = Date()
        val expiryDate = Date(now.time + tokenValidityInMilliseconds)
        return Jwts.builder()
            .subject(userDetails.id.toString())
            .issuer("shopree")
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }

    fun getUserFromJwt(token: String): UUID {
        val claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
        return UUID.fromString(claims.subject)
    }

    fun getJwtPayload(token: String): Claims {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(authToken)
            return true
        } catch (e: MalformedJwtException) {
            println("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            println("Expired JWT token: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            println("Unsupported JWT token: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("JWT claims string is empty: ${e.message}")
        }
        return false
    }
}