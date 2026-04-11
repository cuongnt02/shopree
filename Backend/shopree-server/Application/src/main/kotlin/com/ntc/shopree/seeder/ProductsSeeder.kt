package com.ntc.shopree.seeder

import com.ntc.data.CategoryRepository
import com.ntc.data.ProductRepository
import com.ntc.data.ProductVariantRepository
import com.ntc.data.VendorRepository
import com.ntc.domain.model.Product
import com.ntc.domain.model.ProductVariant
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(4)
class ProductsSeeder(
    private val productRepository: ProductRepository,
    private val productVariantRepository: ProductVariantRepository,
    private val vendorRepository: VendorRepository,
    private val categoryRepository: CategoryRepository
) : CommandLineRunner {

    private data class VariantSeed(
        val title: String,
        val sku: String,
        val priceCents: Long,
        val inventoryCount: Int
    )

    private data class ProductSeed(
        val title: String,
        val slug: String,
        val vendorSlug: String,
        val categorySlug: String,
        val description: String,
        val imageIndex: Int,
        val pickupAvailable: Boolean,
        val variants: List<VariantSeed>
    )

    override fun run(vararg args: String) {
        val vendors = vendorRepository.findAll().associateBy { it.slug }
        val categories = categoryRepository.findAll().associateBy { it.slug }

        if (vendors.isEmpty() || categories.isEmpty()) return

        productRepository.deleteAll()

        val seeds = listOf(
            // Vendor 1 — electronics-store
            ProductSeed("Wireless Noise-Cancelling Headphones", "wireless-noise-cancelling-headphones", "electronics-store", "electronics",
                "Premium wireless headphones with active noise cancellation and 30-hour battery life.", 1, true,
                listOf(
                    VariantSeed("Standard", "wh-standard", 590_000L, 25),
                    VariantSeed("Pro", "wh-pro", 890_000L, 15)
                )
            ),
            ProductSeed("Adjustable Laptop Stand", "adjustable-laptop-stand", "electronics-store", "laptops",
                "Ergonomic aluminium laptop stand with 6 adjustable height levels.", 2, true,
                listOf(VariantSeed("Default", "als-default", 350_000L, 30))
            ),
            ProductSeed("Mechanical Keyboard", "mechanical-keyboard", "electronics-store", "electronics",
                "Tactile mechanical keyboard with RGB backlight and anti-ghosting.", 3, true,
                listOf(
                    VariantSeed("TKL (87 keys)", "mk-tkl", 750_000L, 20),
                    VariantSeed("Full Size (104 keys)", "mk-full", 890_000L, 20)
                )
            ),
            ProductSeed("Portable Charger 20000mAh", "portable-charger-20000mah", "electronics-store", "mobile-phones",
                "Fast-charging power bank with dual USB-A and USB-C output.", 4, true,
                listOf(VariantSeed("Default", "pc-20k-default", 450_000L, 50))
            ),
            ProductSeed("USB-C Hub 7-in-1", "usb-c-hub-7-in-1", "electronics-store", "electronics",
                "Compact USB-C hub with HDMI 4K, 3x USB-A, SD card reader, and PD 100W pass-through.", 5, true,
                listOf(VariantSeed("Default", "hub-7in1-default", 320_000L, 40))
            ),
            ProductSeed("HD Webcam 1080p", "hd-webcam-1080p", "electronics-store", "cameras",
                "Full HD webcam with built-in stereo microphone and auto-focus.", 6, false,
                listOf(VariantSeed("Default", "wc-1080p-default", 480_000L, 20))
            ),
            ProductSeed("LED Desk Lamp with USB Charging", "led-desk-lamp-usb-charging", "electronics-store", "electronics",
                "Touch-dimming LED lamp with USB charging port and 5 colour temperature modes.", 7, true,
                listOf(
                    VariantSeed("White", "lamp-white", 250_000L, 35),
                    VariantSeed("Black", "lamp-black", 250_000L, 35)
                )
            ),
            ProductSeed("Bluetooth Portable Speaker", "bluetooth-portable-speaker", "electronics-store", "electronics",
                "360° sound Bluetooth speaker, IPX7 waterproof, 12-hour playback.", 8, true,
                listOf(VariantSeed("Default", "spk-bt-default", 400_000L, 30))
            ),
            ProductSeed("Smart Watch Fitness Tracker", "smart-watch-fitness-tracker", "electronics-store", "mobile-phones",
                "Heart rate, SpO2, sleep tracking, GPS — compatible with Android and iOS.", 9, true,
                listOf(
                    VariantSeed("S/M", "sw-sm", 699_000L, 20),
                    VariantSeed("L/XL", "sw-lxl", 699_000L, 20)
                )
            ),
            ProductSeed("Magnetic Phone Mount", "magnetic-phone-mount", "electronics-store", "mobile-phones",
                "Universal dashboard magnetic car phone mount with 360° rotation.", 10, true,
                listOf(VariantSeed("Default", "pm-mag-default", 150_000L, 80))
            ),
            // Vendor 2 — skincare-store
            ProductSeed("Vitamin C Brightening Serum", "vitamin-c-brightening-serum", "skincare-store", "skincare",
                "20% Vitamin C serum with hyaluronic acid for radiant, even-toned skin.", 11, false,
                listOf(
                    VariantSeed("30ml", "vc-serum-30", 380_000L, 60),
                    VariantSeed("60ml", "vc-serum-60", 650_000L, 40)
                )
            ),
            ProductSeed("Hydrating Face Cream", "hydrating-face-cream", "skincare-store", "skincare",
                "Lightweight, non-greasy moisturiser with ceramides and niacinamide.", 12, false,
                listOf(VariantSeed("Default", "face-cream-default", 290_000L, 60))
            ),
            ProductSeed("SPF 50 Daily Sunscreen", "spf-50-daily-sunscreen", "skincare-store", "skincare",
                "Broad-spectrum UVA/UVB protection, no white cast, reef-safe formula.", 13, false,
                listOf(VariantSeed("Default", "sunscreen-spf50-default", 220_000L, 75))
            ),
            // Vendor 3 — chemists-store
            ProductSeed("Daily Multivitamin 60 Tablets", "daily-multivitamin-60-tablets", "chemists-store", "beauty-personal-care",
                "Complete A-Z multivitamin with minerals — one tablet per day.", 14, false,
                listOf(VariantSeed("Default", "multivit-60-default", 180_000L, 100))
            ),
            ProductSeed("Antibacterial Hand Sanitizer", "antibacterial-hand-sanitizer", "chemists-store", "beauty-personal-care",
                "70% alcohol gel sanitizer, kills 99.9% of germs, aloe vera infused.", 15, false,
                listOf(
                    VariantSeed("100ml", "sanitizer-100", 45_000L, 200),
                    VariantSeed("500ml", "sanitizer-500", 150_000L, 100)
                )
            ),
        )

        seeds.forEach { seed ->
            val vendor = vendors[seed.vendorSlug] ?: return@forEach
            val category = categories[seed.categorySlug] ?: return@forEach

            val product = productRepository.save(
                Product(
                    vendor = vendor,
                    title = seed.title,
                    slug = seed.slug,
                    description = seed.description,
                    category = category,
                    mainImage = "https://picsum.photos/seed/${seed.imageIndex}/600/400",
                    pickupAvailable = seed.pickupAvailable,
                    status = Product.Status.PUBLISHED,
                )
            )

            seed.variants.forEach { v ->
                productVariantRepository.save(
                    ProductVariant(
                        product = product,
                        title = v.title,
                        sku = v.sku,
                        priceCents = v.priceCents,
                        inventoryCount = v.inventoryCount,
                    )
                )
            }
        }
    }
}
