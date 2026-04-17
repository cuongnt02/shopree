import {useQuery} from "@tanstack/react-query";
import {fetchVariants} from "@/features/products/api.ts";

export function useProductVariants(productId: string) {
    return useQuery({
        queryKey: ['variants', productId],
        queryFn: () => fetchVariants(productId),
        enabled: productId !== ''
    })
}