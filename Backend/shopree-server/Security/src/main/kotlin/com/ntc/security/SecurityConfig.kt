package com.ntc.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.slf4j.LoggerFactory

@Configuration
class SecurityConfig(
    private val userDetailsService: AppUserDetailsService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val environment: Environment
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    }

    @Bean
    fun jwtAuthenticationFilter() =
        JwtAuthenticationFilter(jwtTokenProvider = jwtTokenProvider, userDetailsService = userDetailsService)


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        val isSecurityDisabled = environment.activeProfiles.contains("no-security")
        http {

            csrf { disable() }
            authorizeHttpRequests {
                if (isSecurityDisabled) {
                    logger.warn("============================================================")
                    logger.warn("SECURITY IS DISABLED - 'no-security' profile is active!")
                    logger.warn("All endpoints are publicly accessible without authentication")
                    logger.warn("DO NOT use this configuration in production!")
                    logger.warn("============================================================")
                    authorize(anyRequest, permitAll)
                } else {
                    authorize("/api/v1/categories", hasAuthority("BUYER"))
                    authorize("/api/v1/products", hasAuthority("BUYER"))
                    authorize("/api/v1/product/**", hasAuthority("BUYER"))
                    authorize("/api/v1/auth/**", permitAll)
                    authorize("/v3/api-docs/**", permitAll)
                    authorize("/swagger-ui/**", permitAll)
                    authorize("/error", permitAll)
                }
            }
            // TODO: Somehow implement the Oauth2 Resource Server
//            oauth2ResourceServer {
//                jwt { jwtAuthenticationConverter() }
//            }
            if (!isSecurityDisabled)
                addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtAuthenticationFilter())


            // TODO: Implement remember me
        }


        return http.build()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)

        return ProviderManager(authenticationProvider)

    }

//    @Bean
//    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
//        val grantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
//        grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_")
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope")
//        val jwtAuthenticationConverter = JwtAuthenticationConverter()
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
//        return jwtAuthenticationConverter
//    }


}
