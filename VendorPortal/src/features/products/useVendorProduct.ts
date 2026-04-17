import {fetchVendorProduct} from "@/features/products/api.ts";
import {useQuery} from "@tanstack/react-query";

export function useVendorProduct(id: string | null) {
    return useQuery({
        queryKey: ['vendor-products', id],
        queryFn: () => fetchVendorProduct(id!),
        enabled: id !== null
    })
}