import {useMutation, useQueryClient} from "@tanstack/react-query";
import type {VariantFormData} from "@/types/product.ts";
import {addVariant} from "@/features/products/api.ts";

export function useAddVariant(productId: string) {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (data: VariantFormData) => addVariant(productId, data),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['variants', productId]})
    })
}