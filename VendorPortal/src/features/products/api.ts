import type {ProductFormData, ProductImage, ProductVariant, VariantFormData, VendorProduct} from "@/types/product.ts";
import {api} from "@/lib/axios.ts";
import type {Category} from "@/types/category.ts";

export async function fetchVendorProducts(status?: string): Promise<VendorProduct[]> {
    const params = status ? `?status=${status}` : ''
    const {data} = await api.get<VendorProduct[]>(`/api/v1/vendor/products${params}`)
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

export async function fetchCategories(): Promise<Category[]> {
    const {data} = await api.get<Category[]>('/api/v1/categories')
    return data
}

export async function uploadVariantImage(productId: string, variantId: string, file: File): Promise<ProductVariant> {
    const form = new FormData()
    form.append('file', file)
    const {data} = await api.post<ProductVariant>(`/api/v1/vendor/products/${productId}/variants/${variantId}/image`, form)
    return data
}

export async function fetchVendorProduct(id: string): Promise<VendorProduct> {
    const {data} = await api.get<VendorProduct>(`/api/v1/vendor/products/${id}`)
    return data
}

export async function uploadProductImage(productId: string, file: File, altText?: string): Promise<ProductImage> {
    const form = new FormData()
    form.append("file", file)
    if (altText) form.append("altText", altText)
    const {data} = await api.post<ProductImage>(`/api/v1/vendor/products/${productId}/images`, form)
    return data
}

export async function deleteProductImage(productId: string, imageId: string): Promise<void> {
    await api.delete(`/api/v1/vendor/products/${productId}/images/${imageId}`)
}