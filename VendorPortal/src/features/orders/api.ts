import type {OrderSummary} from "@/types/order.ts";
import {api} from "@/lib/axios.ts";

export async function fetchOrders(): Promise<OrderSummary[]> {
    const { data } = await api.get<OrderSummary[]>('/api/v1/orders')
    return data
}