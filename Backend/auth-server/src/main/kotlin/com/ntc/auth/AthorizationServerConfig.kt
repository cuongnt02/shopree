package com.ntc.auth

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.web.SecurityFilterChain
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.UUID

@Configuration(proxyBeanMethods = false)
class AthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun authorizationServerSecurityFilterChain(
        http: HttpSecurity,

        ): SecurityFilterChain {

        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer()
        val endpointsMatcher = authorizationServerConfigurer.endpointsMatcher

        http {
            securityMatcher(endpointsMatcher)
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
                authorize("/login/**", permitAll)
            }
            csrf { disable() }
        }
        http.with(authorizationServerConfigurer) {
            it.oidc(Customizer.withDefaults())
        }
        http.formLogin(Customizer.withDefaults())

        return http.build()
    }

    @Bean
    fun registeredClientRepository(passwordEncoder: PasswordEncoder): RegisteredClientRepository {
        val oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("oidc-client")
            .clientSecret(passwordEncoder.encode("secret"))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://127.0.0.1:9000/login/oauth2/code/oidc-client")
            .scope("writeCategories")
            .scope("deleteCategories")
            .scope(OidcScopes.OPENID)
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).requireProofKey(true).build())
            .build()

        return InMemoryRegisteredClientRepository(oidcClient)

    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val keyPair: KeyPair = generateRSAKey()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        val rsaKey = RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    @Bean
    fun jwtDecode(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder().build()
    }


    companion object {
        private fun generateRSAKey(): KeyPair {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            return keyPairGenerator.generateKeyPair()
        }
    }

}