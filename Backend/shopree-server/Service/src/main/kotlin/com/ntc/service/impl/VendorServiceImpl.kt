package com.ntc.service.impl

import com.ntc.data.VendorRepository
import com.ntc.service.VendorService
import com.ntc.service.dto.UpdateVendorProfileRequest
import com.ntc.service.dto.VendorProfileResponse
import com.ntc.service.dto.toVendorProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
@Transactional(readOnly = true)
class VendorServiceImpl(private val vendorRepository: VendorRepository) : VendorService {
    override fun getProfile(userId: UUID): VendorProfileResponse {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("Vendor not found for owner user")
        return vendor.toVendorProfileResponse()
    }

    @Transactional
    override fun updateProfile(
        userId: UUID,
        request: UpdateVendorProfileRequest
    ): VendorProfileResponse {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("Vendor not found for owner user")
        val updatedVendor = vendor.copy(
            vendorName = request.vendorName,
            description = request.description ?: vendor.description,
            address = request.address ?: vendor.address,
            pickupAvailable = request.pickupAvailable,
            localDeliveryRadiusKm = request.localDeliveryRadiusKm,
        )
        updatedVendor.updatedAt = Instant.now()
        vendorRepository.save(updatedVendor)
        return updatedVendor.toVendorProfileResponse()
    }
}