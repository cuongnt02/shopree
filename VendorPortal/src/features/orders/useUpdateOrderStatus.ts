import {useMutation, useQueryClient} from "@tanstack/react-query";
import {updateOrderStatus} from "@/features/orders/api.ts";

export function useUpdateOrderStatus() {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: ({ id, status }: {id: string, status: string}) => updateOrderStatus(id, status),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['orders']})
            queryClient.invalidateQueries({ queryKey: ['order']})
        }
    })
}
