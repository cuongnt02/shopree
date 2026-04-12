package com.ntc.service

import com.ntc.service.dto.UpdateVendorProfileRequest
import com.ntc.service.dto.VendorProfileResponse
import java.util.UUID

interface VendorService {
    fun getProfile(userId: UUID): VendorProfileResponse
    fun updateProfile(userId: UUID, request: UpdateVendorProfileRequest): VendorProfileResponse
}