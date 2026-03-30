package com.ntc.service.impl

import com.ntc.data.UserRepository
import com.ntc.domain.exception.AuthenticationException
import com.ntc.domain.exception.NotFoundException
import com.ntc.domain.model.User
import com.ntc.service.UserService
import com.ntc.service.dto.ChangePasswordRequest
import com.ntc.service.dto.UpdateProfileRequest
import com.ntc.service.dto.UserProfileResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) :
    UserService {
    override fun getProfile(userId: UUID): UserProfileResponse {
        val user = userRepository.findById(userId).orElseThrow {
            NotFoundException("User not found")
        }
        return user.toProfileResponse()
    }

    override fun updateProfile(
        userId: UUID,
        request: UpdateProfileRequest
    ): UserProfileResponse {
        val user = userRepository.findById(userId).orElseThrow {
            NotFoundException("User not found")
        }
        request.name?.let { user.name = it }
        request.phone?.let { user.phone = it }
        request.metadata?.let { user.metadata += it }
        user.updatedAt = Instant.now()
        return userRepository.save(user).toProfileResponse()
    }

    override fun changePassword(userId: UUID, request: ChangePasswordRequest) {
        val user = userRepository.findById(userId).orElseThrow {
            NotFoundException("User not found")
        }
        if (!passwordEncoder.matches(request.currentPassword, user.passwordHash)) {
            throw AuthenticationException("Current password is incorrect")
        }
        user.passwordHash = passwordEncoder.encode(request.newPassword)!!
        user.updatedAt = Instant.now()
        userRepository.save(user)
    }

    private fun User.toProfileResponse() = UserProfileResponse(
        id = id.toString(),
        name = name,
        email = email,
        phone = phone,
        role = role.toString(),
        verified = verified,
        metadata = metadata,
        createdAt = createdAt.toEpochMilli()
    )
}