package com.ntc.shopree.seeder

import com.ntc.data.UserRepository
import com.ntc.data.VendorRepository
import com.ntc.shopree.model.Vendor
import org.springframework.boot.CommandLineRunner
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(3)
class VendorSeeder(private val vendorRepository: VendorRepository,
                   private val userRepository: UserRepository
) :
    CommandLineRunner {
    override fun run(vararg args: String) {
        val vendor1 = userRepository.findByEmail("vendor@shopree.com")
        vendor1?.let {
            vendorRepository.save(
                Vendor(
                    vendorName = "Vendor Store 1",
                    description = "First vendor store",
                    slug = "electronics-store",
                    ownerUser = it,
                    status = Vendor.Status.APPROVED
                )
            )
        }

        val vendor2 = userRepository.findByEmail("vendor2@shopree.com")
        vendor2?.let {
            vendorRepository.save(
                Vendor(
                    vendorName = "Vendor Store 2",
                    description = "Second vendor store",
                    slug = "skincare-store",
                    ownerUser = it,
                    status = Vendor.Status.PENDING
                )
            )
        }

        val vendor3 = userRepository.findByEmail("vendor3@shopree.com")
        vendor3?.let {
            vendorRepository.save(
                Vendor(
                    vendorName = "Vendor Store 3",
                    description = "Third vendor store",
                    slug = "chemists-store",
                    ownerUser = it,
                    status = Vendor.Status.REJECTED
                )
            )
        }

    }

}
