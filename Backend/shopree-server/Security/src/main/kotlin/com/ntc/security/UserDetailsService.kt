package com.ntc.security

import com.ntc.data.UserRepository
import com.ntc.domain.model.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AppUserDetailsService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): User {
        val user = if (username.startsWith("+") || username.all { it.isDigit() || it == '+' || it == ' ' }) {
            userRepository.findByPhone(username)
        } else {
            userRepository.findByEmail(username)
        }
        return user ?: throw UsernameNotFoundException("User: $username not found")
    }

    fun loadUserById(id: UUID): User? {
        return userRepository.findById(id).orElse(null)
    }
}

