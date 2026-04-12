package com.ntc.api

import com.ntc.domain.model.User
import com.ntc.service.VendorService
import com.ntc.service.dto.UpdateVendorProfileRequest
import com.ntc.service.dto.VendorProfileResponse
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/vendor", produces = [MediaType.APPLICATION_JSON_VALUE])
class VendorProfileController(private val vendorService: VendorService) {
    
    @GetMapping("/profile")
    fun getProfile(authentication: Authentication): ResponseEntity<VendorProfileResponse> {
        val user = authentication.principal as User
        return try {
            ResponseEntity.ok(vendorService.getProfile(user.id!!))
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/profile")
    fun updateProfile(authentication: Authentication, @Valid @RequestBody request: UpdateVendorProfileRequest): ResponseEntity<VendorProfileResponse> {
        val user = authentication.principal as User
        return try {
            ResponseEntity.ok(vendorService.updateProfile(user.id!!, request))
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
}