import {useMutation, useQueryClient} from "@tanstack/react-query";
import {uploadProductImage} from "@/features/products/api.ts";
import type {ProductImage, VendorProduct} from "@/types/product.ts";

export function useUploadProductImage(productId: string) {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: ({file, altText}: {file: File, altText?: string}) =>
            uploadProductImage(productId, file, altText),
        onSuccess: (newImage: ProductImage) => {
            queryClient.setQueryData(
                ['vendor-products', productId],
                (old: VendorProduct | undefined) => {
                    if (!old) return old
                    return {...old, images: [...(old.images ?? []), newImage]}
                }
            )
            queryClient.invalidateQueries({queryKey: ['vendor-products']})
        }
    })
}