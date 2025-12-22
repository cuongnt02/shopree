package com.ntc.auth

import com.ntc.auth.user.User
import com.ntc.auth.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Order(2)
class UserSeeder(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {
    @Transactional
    override fun run(vararg args: String) {
        userRepository.deleteAll()

        val user1 = User(
            email = "cuong.ntc02@gmail.com",
            password = passwordEncoder.encode("123456789")!!,
            role = User.Role.BUYER
        )
        val saved1 = userRepository.save(user1)
        println("Seeded user: ${saved1.email} with role ${saved1.role}")

        val admin = User(
            email = "admin@shopree.com",
            password = passwordEncoder.encode("admin123")!!,
            role = User.Role.ADMIN
        )
        userRepository.save(admin)

        val vendor1 = User(
            email = "vendor@shopree.com",
            password = passwordEncoder.encode("vendor123")!!,
            role = User.Role.VENDOR_USER
        )
        userRepository.save(vendor1)

        val vendor2 = User(
            email = "vendor2@shopree.com",
            password = passwordEncoder.encode("vendor123")!!,
            role = User.Role.VENDOR_USER
        )
        userRepository.save(vendor2)

        val vendor3 = User(
            email = "vendor3@shopree.com",
            password = passwordEncoder.encode("vendor123")!!,
            role = User.Role.VENDOR_USER
        )
        userRepository.save(vendor3)
    }
}
