import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import {useOrders} from "@/features/orders/useOrders.ts";

function formatVnd(cents: number) {
    return Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(cents)
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
                <Skeleton className="h4 w-24"/>
            </CardHeader>
            <CardContent>
                <Skeleton className="h8 w-16"/>
            </CardContent>
        </Card>
    )
}

export function DashboardPage() {
    const {data: orders, isPending} = useOrders()

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
    const stats = [
        {label: 'Total Orders', value: String(allOrders.length)},
        {label: 'Pending', value: String(allOrders.filter(o => o.status === "PENDING_PAYMENT").length)},
        {label: 'Ready for Pickup', value: String(allOrders.filter(o => o.status === "READY_FOR_PICKUP").length)},
        {label: 'Total Revenue', value: formatVnd(allOrders.reduce((sum, o) => sum + o.totalCents, 0))}
    ]
    return (
        <div className="space-y-4">
            <h1 className="text-2xl font-semibold">Dashboard</h1>
            <div className="grid grid-cols-2 gap-4 lg:grid-cols-4">
                {stats.map(stat => <StatCard label={stat.label} value={stat.value}/>)}
            </div>
        </div>
    )
}