import {keepPreviousData, useQuery} from "@tanstack/react-query";
import {fetchVendorProducts} from "@/features/products/api.ts";
import type {ProductStatus} from "@/types/product.ts";

export function useVendorProducts(status?: ProductStatus) {
    return useQuery({
        queryKey: ['vendor-products', status ?? 'ALL'],
        queryFn: () => fetchVendorProducts(status),
        placeholderData: keepPreviousData
    })
}