import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table.tsx";
import {useVendorProducts} from "@/features/products/useVendorProducts.ts";
import {formatVnd} from "@/lib/currency.ts";
import type {ProductStatus, VendorProduct} from "@/types/product.ts";
import {Badge} from "@/components/ui/badge.tsx";
import {useState} from "react";
import {Button} from "@/components/ui/button.tsx";
import {ProductFormSheet} from "@/features/products/ProductFormSheet.tsx";

const statusVariant: Record<ProductStatus, 'default' | 'secondary' | 'outline'> = {
    PUBLISHED: 'default',
    DRAFT: 'outline',
    DISABLED: 'secondary',
    PENDING_APPROVAL: 'secondary',
}

export function ProductsPage() {
    const [createOpen, setCreateOpen] = useState(false)
    const [editProduct, setEditProduct] = useState<VendorProduct | null>(null)
    const {data: products, isPending, isError} = useVendorProducts()
    if (isPending) return <p className="text-muted-foreground">Loading</p>
    if (isError) return <p className="text-destructive">Failed to load products</p>

    return (
        <div className="space-y-4">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-semibold">Products</h1>
                <Button onClick={() => setCreateOpen(true)}>New Product</Button>
            </div>
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
                        <TableRow key={product.id} className="cursor-pointer" onClick={() => setEditProduct(product)}>
                            <TableCell className="font-mono">
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
                            <TableCell>{product.pickupAvailable ? 'yes': '-'}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            <ProductFormSheet open={createOpen} onClose={() => setCreateOpen(false)} />
            <ProductFormSheet open={editProduct !== null} onClose={() => setEditProduct(null)} product={editProduct ?? undefined} />
        </div>
    )
}