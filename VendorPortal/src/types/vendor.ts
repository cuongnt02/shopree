export interface VendorProfile {
    id: string
    vendorName: string
    slug: string
    description: string | null
    status: string
    address: Record<string, string> | null
    pickupAvailable: boolean
    localDeliveryRadiusKm: number
}

export interface VendorProfileFormData {
    vendorName: string
    description: string
    pickupAvailable: boolean
    localDeliveryRadiusKm: number
    addressStreet: string
    addressCity: string
    addressWard: string
}