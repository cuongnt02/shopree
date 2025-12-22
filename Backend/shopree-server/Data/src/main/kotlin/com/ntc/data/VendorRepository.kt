package com.ntc.data

import com.ntc.shopree.model.Vendor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VendorRepository : CrudRepository<Vendor, UUID> {
    fun getVendorBySlug(slug: String): Vendor?
}