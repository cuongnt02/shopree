import {useMutation, useQueryClient} from "@tanstack/react-query";
import {deleteProductImage} from "@/features/products/api.ts";
import type {VendorProduct} from "@/types/product.ts";

export function useDeleteProductImage(productId: string) {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (imageId: string) => deleteProductImage(productId, imageId),
        onSuccess: (_, imageId: string) => {
            queryClient.setQueryData(
                ['vendor-products', productId],
                (old: VendorProduct | undefined) => {
                    if (!old) return old
                    return {...old, images: old.images.filter(img => img.id !== imageId)}
                }
            )
            queryClient.invalidateQueries({queryKey: ['vendor-products']})
        }
    })
}