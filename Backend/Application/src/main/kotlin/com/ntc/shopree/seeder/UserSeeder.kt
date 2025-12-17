package com.ntc.shopree.seeder

import com.ntc.data.UserRepository
import com.ntc.data.VendorRepository
import com.ntc.shopree.model.User
import com.ntc.shopree.model.Vendor
import jakarta.transaction.Transactional
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.DependsOn
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.UUID

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class UserSeeder(
    private val userRepository: UserRepository,
    private val vendorRepository: VendorRepository,
    private val passwordEncoder: PasswordEncoder
) :
CommandLineRunner {
    @Transactional
    override fun run(vararg args: String) {
        userRepository.deleteAll()

        val user: User? = userRepository.save(
            User(
                email = "cuong.ntc02@gmail.com",
                phone = "1234567890",
                name = "Cuong",
                passwordHash = passwordEncoder.encode("123456789")!!
            )
        )

        println("USER: $user created, password: ${user?.passwordHash}\n${user?.role?.name}")
        userRepository.save(
            User(
                email = "admin@shopree.com",
                phone = "9876543210",
                name = "Admin User",
                passwordHash = passwordEncoder.encode("admin123")!!,
                role = User.Role.ADMIN
            )
        )
        userRepository.save(
            User(
                email = "vendor@shopree.com",
                phone = "5555555555",
                name = "Vendor User",
                passwordHash = passwordEncoder.encode("vendor123")!!,
                role = User.Role.VENDOR_USER
            )
        )
        userRepository.save(
            User(
                email = "vendor2@shopree.com",
                phone = "6666666666",
                name = "Second Vendor",
                passwordHash = passwordEncoder.encode("vendor123")!!,
                role = User.Role.VENDOR_USER
            )
        )
        userRepository.save(
            User(
                email = "vendor3@shopree.com",
                phone = "7777777777",
                name = "Third Vendor",
                passwordHash = passwordEncoder.encode("vendor123")!!,
                role = User.Role.VENDOR_USER
            )
        )


    }

}
