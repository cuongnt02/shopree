import {useMutation, useQueryClient} from "@tanstack/react-query";
import type {ProductFormData} from "@/types/product.ts";
import {createProduct} from "@/features/products/api.ts";

export function useCreateProduct() {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (data: ProductFormData) => createProduct(data),
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ['vendor-products']})
    })
}