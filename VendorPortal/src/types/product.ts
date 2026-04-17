export type ProductStatus = 'PUBLISHED' | 'DRAFT' | 'DISABLED' | 'PENDING_APPROVAL'

export interface VendorProduct {
    id: string
    title: string
    slug: string
    status: ProductStatus
    category: string | null
    mainImage: string | null
    pickupAvailable: boolean
    startingPriceCents: number | null
    images: ProductImage[]
}

export interface ProductFormData {
    title: string
    slug: string
    description: string,
    categorySlug: string
    status: ProductStatus
    pickupAvailable: boolean
    priceCents: number
}

export interface ProductVariant {
    id: string
    title: string | null
    sku: string | null
    priceCents: number
    compareAtCents: number | null
    inventoryCount: number
    image: string | null
}

export interface VariantFormData {
    title: string
    sku: string
    priceCents: number
    compareAtCents: number
    inventoryCount: number
}

export interface ProductImage {
    id: string
    url: string
    altText: string | null
    position: number
}