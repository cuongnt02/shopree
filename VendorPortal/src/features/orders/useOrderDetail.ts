import {useQuery} from "@tanstack/react-query";
import {fetchOrderDetail} from "@/features/orders/api.ts";

export function useOrderDetail(id: string | null) {
    return useQuery({
        queryKey: ['order', id],
        queryFn: () => fetchOrderDetail(id!),
        enabled: id !== null
    })
}