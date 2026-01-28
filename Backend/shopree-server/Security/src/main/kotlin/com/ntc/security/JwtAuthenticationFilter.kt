package com.ntc.security

import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.AntPathMatcher
import org.springframework.util.StringUtils


class JwtAuthenticationFilter(private val jwtTokenProvider: JwtTokenProvider, private val userDetailsService: AppUserDetailsService): OncePerRequestFilter() {
    private val pathMatcher = AntPathMatcher()
    private val publicPaths = listOf(
        "/api/v1/auth/**",
        "/v3/api-docs/**",
        "/swagger-ui.html/**",
        "/swagger-ui/**",
        "/error"
    )
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwtFromRequest(request)

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt!!)) {
                val userId = jwtTokenProvider.getUserFromJwt(jwt)

                val userDetails = userDetailsService.loadUserById(userId)

                if (userDetails != null) {
                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                    println("User authenticated successfully: ${authentication.principal}")
                    println("User authorities: ${authentication.authorities}")
                }
            }
        } catch (e: Exception) {
            println("Could not set user authentication in security context")
        } finally {
            filterChain.doFilter(request, response)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return publicPaths.any { pattern -> pathMatcher.match(pattern, path) }
    }

    fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization") ?: return null
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }


}