import {useMutation, useQueryClient} from "@tanstack/react-query";
import type {ProductFormData} from "@/types/product.ts";
import {updateProduct} from "@/features/products/api.ts";

export function useUpdateProduct() {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: ({id, data}: {id: string, data: ProductFormData}) => updateProduct(id, data),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['vendor-products']})
    })
}