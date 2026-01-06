package com.ntc.security

import com.ntc.data.UserRepository
import com.ntc.shopree.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AppUserDetailsService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): User {
        return userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User: $username not found")
    }

    fun loadUserById(id: UUID): User? {
        return userRepository.findById(id).orElse(null)
    }
}

