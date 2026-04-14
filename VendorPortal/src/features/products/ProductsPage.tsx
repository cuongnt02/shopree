import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table.tsx";
import {useVendorProducts} from "@/features/products/useVendorProducts.ts";
import {formatVnd} from "@/lib/currency.ts";
import type {ProductStatus, VendorProduct} from "@/types/product.ts";
import {Badge} from "@/components/ui/badge.tsx";
import {useState} from "react";
import {Button} from "@/components/ui/button.tsx";
import {ProductFormSheet} from "@/features/products/ProductFormSheet.tsx";
import {ProductDetailSheet} from "@/features/products/ProductDetailSheet.tsx";
import {Tabs, TabsList, TabsTrigger} from "@/components/ui/tabs.tsx";

const statusVariant: Record<ProductStatus, 'default' | 'secondary' | 'outline'> = {
    PUBLISHED: 'default',
    DRAFT: 'outline',
    DISABLED: 'secondary',
    PENDING_APPROVAL: 'secondary',
}

export function ProductsPage() {
    const [createOpen, setCreateOpen] = useState(false)
    const [detailProduct, setDetailProduct] = useState<VendorProduct | null>(null)
    const [editProduct, setEditProduct] = useState<VendorProduct | null>(null)
    const [statusFilter, setStatusFilter] = useState<ProductStatus | undefined>(undefined)
    const {data: products, isPending, isError, isFetching} = useVendorProducts(statusFilter)
    if (isPending) return <p className="text-muted-foreground">Loading</p>
    if (isError) return <p className="text-destructive">Failed to load products</p>

    function handleEditFromDetail(product: VendorProduct) {
        setDetailProduct(null)
        setEditProduct(product)
    }

    return (
        <div className="space-y-4">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-semibold">Products</h1>
                <Button onClick={() => setCreateOpen(true)}>New Product</Button>
            </div>
            <Tabs value={statusFilter ?? 'ALL'} onValueChange={(v) => setStatusFilter(v === 'ALL' ? undefined : v as ProductStatus)}>
                <TabsList>
                    <TabsTrigger value="ALL">All</TabsTrigger>
                    <TabsTrigger value="PUBLISHED">Published</TabsTrigger>
                    <TabsTrigger value="DRAFT">Draft</TabsTrigger>
                    <TabsTrigger value="DISABLED">Disabled</TabsTrigger>
                    <TabsTrigger value="PENDING_APPROVAL">Pending Approval</TabsTrigger>
                </TabsList>
            </Tabs>
            <div className={isFetching ? 'opacity-50 pointer-events-none transition-opacity' : ''}>
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>Image</TableHead>
                            <TableHead>Name</TableHead>
                            <TableHead>Category</TableHead>
                            <TableHead>Price</TableHead>
                            <TableHead>Status</TableHead>
                            <TableHead>Pickup</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {products?.map((product) => (
                            <TableRow key={product.id} className="cursor-pointer"
                                      onClick={() => setDetailProduct(product)}>
                                <TableCell>
                                    {product.mainImage ? (
                                        <img src={product.mainImage} alt={product.title}
                                             className="w-10 h-10 rounded object-cover"/>
                                    ) : (
                                        <div className="w-10 h-10 rounded bg-muted"/>
                                    )}
                                </TableCell>
                                <TableCell>{product.title}</TableCell>
                                <TableCell>{product.category}</TableCell>
                                <TableCell>{product.startingPriceCents !== null ? formatVnd(product.startingPriceCents) : '-'}</TableCell>
                                <TableCell>
                                    <Badge variant={statusVariant[product.status]}>{product.status.replace(/_/g, ' ')}</Badge>
                                </TableCell>
                                <TableCell>{product.pickupAvailable ? 'yes' : '-'}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
            <ProductFormSheet open={createOpen} onClose={() => setCreateOpen(false)}/>
            <ProductFormSheet open={editProduct !== null} onClose={() => setEditProduct(null)}
                              product={editProduct ?? undefined}/>
            <ProductDetailSheet open={detailProduct !== null} onClose={() => setDetailProduct(null)}
                                product={detailProduct} onEditProduct={handleEditFromDetail}/>
        </div>
    )
}