import type {ProductFormData, VendorProduct} from "@/types/product.ts";
import {api} from "@/lib/axios.ts";

export async function fetchVendorProducts(): Promise<VendorProduct[]> {
    const {data} = await api.get<VendorProduct[]>('/api/v1/vendor/products')
    return data
}

export async function createProduct(data: ProductFormData): Promise<VendorProduct> {
    const {data: response} = await api.post<VendorProduct>('/api/v1/vendor/products', data)
    return response
}

export async function updateProduct(id: string, data: ProductFormData): Promise<VendorProduct> {
    const {data: response} = await api.put<VendorProduct>(`/api/v1/vendor/products/${id}`, data)
    return response
}