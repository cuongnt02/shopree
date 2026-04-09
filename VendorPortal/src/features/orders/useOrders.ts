import {useQuery} from "@tanstack/react-query";
import {fetchOrders} from "@/features/orders/api.ts";

export function useOrders() {
    return useQuery({
        queryKey: ['orders'],
        queryFn: fetchOrders
    })
}