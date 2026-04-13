import {useMutation, useQueryClient} from "@tanstack/react-query";
import type {VariantFormData} from "@/types/product.ts";
import {updateVariant} from "@/features/products/api.ts";

export function useUpdateVariant(productId: string) {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: ({variantId, data}: {
            variantId: string,
            data: VariantFormData
        }) => updateVariant(productId, variantId, data),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['variants', productId]})
    })
}