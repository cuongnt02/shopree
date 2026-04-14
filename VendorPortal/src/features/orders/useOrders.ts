import {keepPreviousData, useQuery} from "@tanstack/react-query";
import {fetchOrders} from "@/features/orders/api.ts";
import type {OrderStatus} from "@/types/order.ts";

export function useOrders(status?: OrderStatus) {
    return useQuery({
        queryKey: ['orders', status ?? 'ALL'],
        queryFn: () => fetchOrders(status),
        placeholderData: keepPreviousData
    })
}