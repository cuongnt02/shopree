import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import {useOrders} from "@/features/orders/useOrders.ts";
import {formatVnd} from "@/lib/currency.ts";
import {Button} from "@/components/ui/button.tsx";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table.tsx";
import {Badge} from "@/components/ui/badge.tsx";
import type {OrderStatus} from "@/types/order.ts";
import {Link} from "react-router";
import {useVendorProducts} from "@/features/products/useVendorProducts.ts";

const statusVariant: Record<OrderStatus, 'default' | 'secondary' | 'destructive' | 'outline'> = {
    PENDING_PAYMENT: 'outline',
    PAID: 'secondary',
    READY_FOR_PICKUP: 'default',
    PICKED_UP: 'secondary',
    CANCELLED: 'destructive',
    REFUNDED: 'destructive',
}


function StatCard({label, value}: { label: string, value: string }) {
    return (
        <Card>
            <CardHeader className="pb-2">
                <CardTitle className="text-sm font-medium text-muted-foreground">{label}</CardTitle>
            </CardHeader>
            <CardContent>
                <p className="text-2xl font-bold">{value}</p>
            </CardContent>
        </Card>
    )
}

function StatCardSkeleton() {
    return (
        <Card>
            <CardHeader className="pb-2">
                <Skeleton className="h-4 w-24"/>
            </CardHeader>
            <CardContent>
                <Skeleton className="h-8 w-16"/>
            </CardContent>
        </Card>
    )
}

export function DashboardPage() {

    const {data: orders, isPending: ordersLoading} = useOrders()
    const {data: products, isPending: productsLoading} = useVendorProducts()

    const isPending = ordersLoading || productsLoading

    if (isPending) {
        return (
            <div className="space-y-4">
                <h1 className="text-2xl font-semibold">Dashboard</h1>
                <div className="grid grid-cols-2 gap-4 lg:grid-cols-4">
                    {Array.from({length: 4}).map((_, i) => <StatCardSkeleton key={i}/>)}
                </div>
            </div>
        )
    }

    const allOrders = orders ?? []
    const allProducts = products ?? []
    const activeProductsCount = allProducts.filter(p => p.status === "PUBLISHED").length
    const stats = [
        {label: 'Active Products', value: String(activeProductsCount)},
        {label: 'Pending', value: String(allOrders.filter(o => o.status === "PENDING_PAYMENT").length)},
        {label: 'Ready for Pickup', value: String(allOrders.filter(o => o.status === "READY_FOR_PICKUP").length)},
        {
            label: 'Total Revenue',
            value: formatVnd(allOrders.filter(o => o.status !== "CANCELLED" && o.status !== "REFUNDED").reduce((sum, o) => sum + o.totalCents, 0))
        }
    ]
    const recentOrders = [...allOrders]
        .sort((a, b) => new Date(b.placedAt).getTime() - new Date(a.placedAt).getTime())
        .slice(0, 5)
    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Dashboard</h1>
            <div className="grid grid-cols-2 gap-4 lg:grid-cols-4">
                {stats.map(stat => <StatCard key={stat.label} label={stat.label} value={stat.value}/>)}
            </div>
            <div className="space-y-2">
                <div className="flex items-center justify-between">
                    <h2 className="text-lg font-semibold">Recent Orders</h2>
                    <Button variant="link" asChild>
                        <Link to="/orders">View all</Link>
                    </Button>
                </div>
                {recentOrders.length === 0
                    ? <p className="text-sm text-muted-foreground py-4 text-center">No orders yet.</p>
                    : <Table>
                        <TableHeader>
                            <TableRow>
                                <TableHead>Order #</TableHead>
                                <TableHead>Status</TableHead>
                                <TableHead>Items</TableHead>
                                <TableHead>Total</TableHead>
                                <TableHead>Placed At</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {recentOrders.map(order => (
                                <TableRow key={order.id}>
                                    <TableCell className="font-mono">{order.orderNumber}</TableCell>
                                    <TableCell>
                                        <Badge variant={statusVariant[order.status]}>
                                            {order.status.replace(/_/g, ' ')}
                                        </Badge>
                                    </TableCell>
                                    <TableCell>{order.itemCount}</TableCell>
                                    <TableCell>{formatVnd(order.totalCents)}</TableCell>
                                    <TableCell>{new Date(order.placedAt).toLocaleString('vi-VN')}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>}

            </div>
        </div>
    )
}