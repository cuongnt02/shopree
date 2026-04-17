import {useMutation, useQueryClient} from "@tanstack/react-query";
import {uploadVariantImage} from "@/features/products/api.ts";
import type {ProductVariant} from "@/types/product.ts";

export function useUploadVariantImage(productId: string) {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: ({variantId, file}: {variantId: string, file: File}) =>
            uploadVariantImage(productId, variantId, file),
        onSuccess: (updatedVariant: ProductVariant) => {
            queryClient.setQueryData(
                ['variants', productId],
                (old: ProductVariant[] | undefined) => {
                    if (!old) return old
                    return old.map(v => v.id === updatedVariant.id ? updatedVariant : v)
                }
            )
        }
    })
}
