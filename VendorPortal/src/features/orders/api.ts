import type {OrderDetail, OrderSummary} from "@/types/order.ts";
import {api} from "@/lib/axios.ts";

export async function fetchOrders(): Promise<OrderSummary[]> {
    const {data} = await api.get<OrderSummary[]>('/api/v1/vendor/orders')
    return data
}

export async function updateOrderStatus(id: string, status: string): Promise<void> {
    await api.patch(`/api/v1/vendor/orders/${id}/status`, {status})
}

export async function fetchOrderDetail(id: string): Promise<OrderDetail> {
    const {data} = await api.get<OrderDetail>(`/api/v1/vendor/order/${id}`)
    return data
}