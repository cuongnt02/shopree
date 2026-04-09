import type {OrderStatus} from "@/types/order.ts";
import {useOrders} from "@/features/orders/useOrders.ts";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table.tsx";
import {Badge} from "@/components/ui/badge.tsx";
import {useUpdateOrderStatus} from "@/features/orders/useUpdateOrderStatus.ts";
import {Button} from "@/components/ui/button.tsx";
import {useState} from "react";
import {useOrderDetail} from "@/features/orders/useOrderDetail.ts";
import {Sheet, SheetContent, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";

const statusVariant: Record<OrderStatus, 'default' | 'secondary' | 'destructive' | 'outline'> = {
    PENDING_PAYMENT: 'outline',
    PAID: 'secondary',
    READY_FOR_PICKUP: 'default',
    PICKED_UP: 'secondary',
    CANCELLED: 'destructive',
    REFUNDED: 'destructive',
}

const nextActions: Partial<Record<OrderStatus, { label: string, status: OrderStatus }[]>> = {
    PENDING_PAYMENT: [
        {label: 'Accept', status: 'PAID'},
        {label: 'Decline', status: 'CANCELLED'},
    ],
    PAID: [
        {label: 'Ready for Pickup', status: 'READY_FOR_PICKUP'},
    ],
    READY_FOR_PICKUP: [
        {label: 'Mark Picked Up', status: 'PICKED_UP'}
    ]
}

function formatVnd(cents: number) {
    return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(cents)
}

export function OrdersPage() {
    const [selectedId, setSelectedId] = useState<string | null>(null)
    const {data: orders, isPending, isError} = useOrders()
    const {data: detail, isPending: isDetailPending} = useOrderDetail(selectedId)
    const {mutate, isPending: isUpdating} = useUpdateOrderStatus()
    if (isPending) return <p className="text-muted-foreground">Loading</p>
    if (isError) return <p className="text-destructive">Failed to load orders.</p>

    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Orders</h1>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>Order #</TableHead>
                        <TableHead>Status</TableHead>
                        <TableHead>Items</TableHead>
                        <TableHead>Total</TableHead>
                        <TableHead>Placed At</TableHead>
                        <TableHead>Actions</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {orders.map((order) => (
                        <TableRow key={order.id} className="cursor-pointer" onClick={() => setSelectedId(order.id!)}>
                            <TableCell className="font-mono">{order.orderNumber}</TableCell>
                            <TableCell>
                                <Badge variant={statusVariant[order.status]}>
                                    {order.status.replace(/_/g, ' ')}
                                </Badge>
                            </TableCell>
                            <TableCell>{order.itemCount}</TableCell>
                            <TableCell>{formatVnd(order.totalCents)}</TableCell>
                            <TableCell>{new Date(order.placedAt).toLocaleString('vi-VN')}</TableCell>
                            <TableCell onClick={(e) => e.stopPropagation()}>
                                <div className="flex gap-2">
                                    {(nextActions[order.status] ?? []).map(action => (
                                        <Button
                                            key={action.status}
                                            size="sm"
                                            variant={action.status === "CANCELLED" ? 'destructive' : 'default'}
                                            disabled={isUpdating}
                                            onClick={() => mutate({id: order.id!, status: action.status})}
                                        >
                                            {action.label}
                                        </Button>
                                    ))}
                                </div>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            <Sheet open={selectedId !== null} onOpenChange={(open) => !open && setSelectedId(null)}>
                <SheetContent className="w-[480px] sm:max-w-[480px] overflow-y-auto">
                    {isDetailPending || !detail ? (
                        <div className="space-y-4 mt-6">
                            <Skeleton className="h-6 w-40"/>
                            <Skeleton className="h-4 w-24"/>
                            <Skeleton className="h-32 w-full"/>
                        </div>
                    ) : (
                        <>
                            <SheetHeader className="mb-6">
                                <SheetTitle className="font-mono">{detail.orderNumber}</SheetTitle>
                                <div className="flex items-center gap-2">
                                    <Badge variant={statusVariant[detail.status]}>
                                        {detail.status.replace(/_/g, ' ')}
                                    </Badge>
                                    <span className="text-sm text-muted-foreground">
                                        {new Date(detail.placedAt).toLocaleString('vi-VN')}
                                    </span>
                                </div>
                            </SheetHeader>
                            <div className="space-y-6">
                                <div>
                                    <h3 className="font-medium mb-3">Items</h3>
                                    <Table>
                                        <TableHeader>
                                            <TableRow>
                                                <TableHead>Product</TableHead>
                                                <TableHead>SKU</TableHead>
                                                <TableHead>Qty</TableHead>
                                                <TableHead>Total</TableHead>
                                            </TableRow>
                                        </TableHeader>
                                        <TableBody>
                                            {detail.items.map((item) => (
                                                <TableRow key={item.id}>
                                                    <TableCell>{item.productTitle}</TableCell>
                                                    <TableCell
                                                        className="font-mono text-xs">{item.sku ?? '-'}</TableCell>
                                                    <TableCell>{item.quantity}</TableCell>
                                                    <TableCell>{formatVnd(item.totalPriceCents)}</TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                    <div className="text-right font-semibold mt-2">
                                        Total: {formatVnd(detail.totalCents)}
                                    </div>
                                </div>
                                {detail.payment && (
                                    <div>
                                        <h3 className="font-medium mb-2">Payment</h3>
                                        <p className="text-sm text-muted-foreground">
                                            {detail.payment.paymentMethod} - {detail.payment.status}
                                        </p>
                                    </div>
                                )}
                                <div className="flex gap-2 pt-2">
                                    {(nextActions[detail.status] ?? []).map(action => (
                                        <Button
                                            key={action.status}
                                            variant={action.status === "CANCELLED" ? 'destructive' : 'default'}
                                            disabled={isUpdating}
                                            onClick={() => mutate({id: detail.id, status: action.status})}
                                        >
                                            {action.label}
                                        </Button>
                                    ))}
                                </div>

                            </div>
                        </>
                    )}
                </SheetContent>
            </Sheet>
        </div>
    )
}