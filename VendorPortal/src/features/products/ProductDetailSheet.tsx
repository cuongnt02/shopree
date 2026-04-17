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
import {Trash, X} from "lucide-react";
import {PencilSimple} from "@phosphor-icons/react";
import {useVendorProduct} from "@/features/products/useVendorProduct.ts";
import {useDeleteProductImage} from "@/features/products/useDeleteProductImage.ts";
import {useUploadProductImage} from "@/features/products/useUploadProductImage.ts";

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
    const [editVariantId, setEditVariantId] = useState<string | null>(null)

    const {data: detail, isPending: isDetailPending} = useVendorProduct(product?.id ?? null)
    const deleteImage = useDeleteProductImage(product?.id ?? '')
    const uploadImage = useUploadProductImage(product?.id ?? '')
    const {data: variants, isPending} = useProductVariants(product?.id ?? '')
    const deleteVariant = useDeleteVariant(product?.id ?? '')
    const editVariant = variants?.find(v => v.id === editVariantId) ?? null

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
                                                onClick={() => setEditVariantId(variant.id)}>
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
                        <div>
                            <h3 className="font-medium mb-3">Images</h3>
                            {isDetailPending
                                ? <Skeleton className="h-20 w-full mb-3"/>
                                : <div className="flex flex-wrap gap-2 mb-3">
                                    {detail?.images?.map(img => (
                                        <div key={img.id} className="relative group w-20 h-20">
                                            <img src={img.url} alt={img?.altText ?? ""} className="w-20 h-20 rounded object-cover"/>
                                            <button onClick={() => deleteImage.mutate(img.id)} className="absolute top-1 right-1 hidden group-hover:flex bg-black/60 text-white rounded p-0.5"><X size={12} /></button>
                                        </div>
                                    )) ?? []}
                                </div>
                            }
                            <label className="cursor-pointer">
                                <input type="file" accept="image/*" className="hidden" onChange={(e) => {
                                    const file = e.target.files?.[0]
                                    if (file) {
                                        uploadImage.mutate({file})
                                        e.target.value = ''
                                    }

                                }}/>
                                <Button variant="outline" size="sm" asChild>
                                    <span>{uploadImage.isPending ? 'Uploading...' : 'Add Image'}</span>
                                </Button>
                            </label>
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
                open={editVariantId !== null}
                onClose={() => setEditVariantId(null)}
                productId={product.id}
                variant={editVariant ?? undefined}
            />
        </>
    )
}
