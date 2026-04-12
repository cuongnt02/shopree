import type {VendorProfile, VendorProfileFormData} from "@/types/vendor.ts";
import {api} from "@/lib/axios.ts";

export async function fetchVendorProfile(): Promise<VendorProfile> {
    const {data} = await api.get<VendorProfile>('/api/v1/vendor/profile')
    return data
}

export async function updateVendorProfile(form: VendorProfileFormData): Promise<VendorProfile> {
    const {addressStreet, addressCity, addressWard, ...rest} = form
    const {data} = await api.put<VendorProfile>('/api/v1/vendor/profile', {
        ...rest,
        address: {street: addressStreet, city: addressCity, ward: addressWard}
    })
    return data
}