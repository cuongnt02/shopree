package com.ntc.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(private val userDetailsService: UserDetailsService) {



    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    }



    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {

            formLogin { }
            authorizeHttpRequests {
//            authorize("/api/v1/categories", hasRole("ROLE_BUYER"))
//            authorize("/api/v1/products", hasRole("ROLE_BUYER"))
//            authorize("/api/v1/users", hasAuthority("ROLE_ADMIN"))
//            authorize("/api/v1/auth/**", permitAll)
            authorize("/**", authenticated)
            }

            // TODO: Implement remember me
        }

        return http.build()
    }

    @Bean
    fun authenticationManager(userDetailsService: UserDetailsService, passwordEncoder: PasswordEncoder): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)

        return ProviderManager(authenticationProvider)

    }


}
