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