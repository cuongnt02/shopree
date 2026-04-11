import {useQuery} from "@tanstack/react-query";
import {fetchVendorProducts} from "@/features/products/api.ts";

export function useVendorProducts() {
    return useQuery({
        queryKey: ['vendor-products'],
        queryFn: fetchVendorProducts
    })
}