export type OrderStatus =
    | 'PENDING_PAYMENT'
    | 'PAID'
    | 'READY_FOR_PICKUP'
    | 'PICKED_UP'
    | 'CANCELLED'
    | 'REFUNDED'

export interface OrderSummary {
    id: string,
    orderNumber: string,
    status: OrderStatus,
    totalCents: number,
    currency: string,
    placedAt: string,
    itemCount: number
}