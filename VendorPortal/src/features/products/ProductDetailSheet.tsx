import {useState} from "react";
import type {ProductStatus, ProductVariant, VendorProduct} from "@/types/product.ts";
import {Sheet, SheetContent, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Badge} from "@/components/ui/badge.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import {useProductVariants} from "@/features/products/useProductVariants.ts";
import {useDeleteVariant} from "@/features/products/useDeleteVariant.ts";
import {VariantFormSheet} from "@/features/products/VariantFormSheet.tsx";
import {formatVnd} from "@/lib/currency.ts";
import {PencilSimple, Trash} from "@phosphor-icons/react";

const statusVariant: Record<ProductStatus, 'default' | 'secondary' | 'outline'> = {
    PUBLISHED: 'default',
    DRAFT: 'outline',
    DISABLED: 'secondary',
    PENDING_APPROVAL: 'secondary',
}

interface Props {
    open: boolean
    onClose: () => void
    product: VendorProduct | null
    onEditProduct: (product: VendorProduct) => void
}

export function ProductDetailSheet({open, onClose, product, onEditProduct}: Props) {
    const [addOpen, setAddOpen] = useState(false)
    const [editVariant, setEditVariant] = useState<ProductVariant | null>(null)

    const {data: variants, isPending} = useProductVariants(product?.id ?? null)
    const deleteVariant = useDeleteVariant(product?.id ?? '')

    if (!product) return null

    return (
        <>
            <Sheet open={open} onOpenChange={(open) => !open && onClose()}>
                <SheetContent className="w-120 sm:max-w-120 overflow-y-auto">
                    <SheetHeader className="mb-6">
                        <div className="flex items-start justify-between gap-4 pr-6">
                            <div>
                                <SheetTitle>{product.title}</SheetTitle>
                                <p className="text-xs text-muted-foreground mt-0.5">/{product.slug}</p>
                            </div>
                            <Badge variant={statusVariant[product.status]}>
                                {product.status.replace(/_/g, ' ')}
                            </Badge>
                        </div>
                    </SheetHeader>

                    <div className="space-y-6 px-1">
                        <Button variant="outline" size="sm" onClick={() => onEditProduct(product)}>
                            Edit Product
                        </Button>

                        <div className="space-y-3">
                            <div className="flex items-center justify-between">
                                <p className="text-sm font-medium">Variants</p>
                                <Button size="sm" onClick={() => setAddOpen(true)}>Add Variant</Button>
                            </div>

                            {isPending && (
                                <div className="space-y-2">
                                    {Array.from({length: 3}).map((_, i) => (
                                        <Skeleton key={i} className="h-12 w-full"/>
                                    ))}
                                </div>
                            )}

                            {!isPending && variants?.length === 0 && (
                                <p className="text-sm text-muted-foreground">No variants yet.</p>
                            )}

                            {variants?.map((variant) => (
                                <div key={variant.id}
                                     className="flex items-center justify-between rounded border px-3 py-2">
                                    <div className="space-y-0.5">
                                        <p className="text-sm font-medium">{variant.title ?? 'Default'}</p>
                                        <div className="flex items-center gap-3 text-xs text-muted-foreground">
                                            <span>{formatVnd(variant.priceCents)}</span>
                                            {variant.sku && <span>SKU: {variant.sku}</span>}
                                            <span>Stock: {variant.inventoryCount}</span>
                                        </div>
                                    </div>
                                    <div className="flex items-center gap-1">
                                        <Button variant="ghost" size="icon"
                                                onClick={() => setEditVariant(variant)}>
                                            <PencilSimple/>
                                        </Button>
                                        <Button variant="ghost" size="icon"
                                                className="text-destructive hover:text-destructive"
                                                disabled={deleteVariant.isPending}
                                                onClick={() => deleteVariant.mutate(variant.id)}>
                                            <Trash/>
                                        </Button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </SheetContent>
            </Sheet>

            <VariantFormSheet
                open={addOpen}
                onClose={() => setAddOpen(false)}
                productId={product.id}
            />
            <VariantFormSheet
                open={editVariant !== null}
                onClose={() => setEditVariant(null)}
                productId={product.id}
                variant={editVariant ?? undefined}
            />
        </>
    )
}
