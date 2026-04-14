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

export interface OrderItem {
    id: string
    productTitle: string
    productSlug: string
    sku: string | null
    quantity: number
    unitPriceCents: number
    totalPriceCents: number
}

export interface OrderPayment {
    paymentMethod: string
    status: string
    amountCents: number
    currency: string
}


export interface OrderDetail {
    id: string
    orderNumber: string
    status: OrderStatus
    totalCents: number
    currency: string
    placedAt: string
    items: OrderItem[]
    payment: OrderPayment | null,
    customer: OrderCustomer
}

export interface OrderCustomer {
    id: string
    name: string
    email: string
}