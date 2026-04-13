import type {ProductFormData, ProductVariant, VariantFormData, VendorProduct} from "@/types/product.ts";
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

export async function fetchVariants(productId: string): Promise<ProductVariant[]> {
    const {data} = await api.get<ProductVariant[]>(`/api/v1/vendor/products/${productId}/variants`)
    return data
}

export async function addVariant(productId: string, data: VariantFormData): Promise<ProductVariant> {
    const {data: response} = await api.post<ProductVariant>(`/api/v1/vendor/products/${productId}/variants`, {
        ...data,
        title: data.title || null,
        sku: data.sku || null,
        compareAtCents: data.compareAtCents > 0 ? data.compareAtCents : null
    })
    return response
}

export async function updateVariant(productId: string, variantId: string, data: VariantFormData): Promise<ProductVariant> {
    const {data: response} = await api.put<ProductVariant>(`/api/v1/vendor/products/${productId}/variants/${variantId}`, {
        ...data,
        title: data.title || null,
        sku: data.sku || null,
        compareAtCents: data.compareAtCents > 0 ? data.compareAtCents : null
    })
    return response
}

export async function deleteVariant(productId: string, variantId: string): Promise<void> {
    await api.delete(`/api/v1/vendor/products/${productId}/variants/${variantId}`)
}