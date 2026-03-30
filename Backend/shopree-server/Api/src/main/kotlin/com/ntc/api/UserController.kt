package com.ntc.api

import com.ntc.api.payload.request.ChangePasswordRequest
import com.ntc.api.payload.request.UpdateProfileRequest
import com.ntc.domain.model.User
import com.ntc.service.UserService
import com.ntc.service.dto.UserProfileResponse
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users", produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(private val userService: UserService) {

    @GetMapping("/me")
    fun getProfile(authentication: Authentication): ResponseEntity<UserProfileResponse> {
        val user = authentication.principal as User
        return ResponseEntity.ok(userService.getProfile(user.id!!))
    }

    @PatchMapping("/me")
    fun updateProfile(
        authentication: Authentication,
        @Valid @RequestBody request: UpdateProfileRequest
    ): ResponseEntity<UserProfileResponse> {
        val user = authentication.principal as User
        val serviceRequest = com.ntc.service.dto.UpdateProfileRequest(
            name = request.name,
            phone = request.phone,
            metadata = request.metadata
        )
        return ResponseEntity.ok(userService.updateProfile(user.id!!, serviceRequest))
    }

    @PostMapping("/me/password")
    fun changePassword(
        authentication: Authentication,
        @Valid @RequestBody request: ChangePasswordRequest
    ): ResponseEntity<Void> {
        val user = authentication.principal as User
        val serviceRequest = com.ntc.service.dto.ChangePasswordRequest(
            currentPassword = request.currentPassword,
            newPassword = request.newPassword
        )
        userService.changePassword(user.id!!, serviceRequest)
        return ResponseEntity.noContent().build()
    }
}