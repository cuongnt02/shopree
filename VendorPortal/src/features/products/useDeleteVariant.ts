import {useMutation, useQueryClient} from "@tanstack/react-query";
import {deleteVariant} from "@/features/products/api.ts";

export function useDeleteVariant(productId: string) {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (variantId: string) => deleteVariant(productId, variantId),
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ['variants', productId]})
    })
}