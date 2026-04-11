import type {VendorProduct} from "@/types/product.ts";
import {useCreateProduct} from "@/features/products/useCreateProduct.ts";
import {useUpdateProduct} from "@/features/products/useUpdateProduct.ts";
import {z} from "zod";
import {useForm, useWatch} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {useEffect} from "react";
import {Sheet, SheetContent, SheetFooter, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";
import {ShopreeSelect} from "@/components/shopree/ShopreeSelect.tsx";
import {ShopreeCheckbox} from "@/components/shopree/ShopreeCheckbox.tsx";

const schema = z.object({
    title: z.string().min(1, 'Title is required'),
    slug: z.string().min(1, 'Slug is required'),
    description: z.string(),
    categorySlug: z.string(),
    status: z.enum(['PUBLISHED', 'DRAFT', 'DISABLED', 'PENDING_APPROVAL']),
    pickupAvailable: z.boolean(),
    priceCents: z.number({error: 'Price is required'}).positive('Must be > 0')
})

type FormValues = z.infer<typeof schema>

interface ProductFormSheetProps {
    open: boolean
    onClose: () => void
    product?: VendorProduct
}

export function ProductFormSheet({open, onClose, product}: ProductFormSheetProps) {
    const isEdit = product !== undefined
    const create = useCreateProduct()
    const update = useUpdateProduct()
    const isPending = create.isPending || update.isPending

    const {register, handleSubmit, control, reset, setValue, formState: {errors}} = useForm<FormValues>({
        resolver: zodResolver(schema),
        defaultValues: {
            title: '', slug: '', description: '', categorySlug: '',
            status: 'DRAFT', pickupAvailable: false, priceCents: 0
        }
    })

    useEffect(() => {
        if (open) {
            reset(isEdit ? {
                title: product.title,
                slug: product.slug,
                description: '',
                categorySlug: product.category ?? '',
                status: product.status,
                pickupAvailable: product.pickupAvailable,
                priceCents: product.startingPriceCents ?? 0,
            } : {
                title: '', slug: '', description: '', categorySlug: '',
                status: 'DRAFT', pickupAvailable: false, priceCents: 0
            })
        }
    }, [isEdit, open, product, reset])

    const title = useWatch({control, name: 'title'})
    useEffect(() => {
        if (!isEdit) {
            setValue('slug', title.toLowerCase().replace(/\s/g, '-').replace(/[^a-z0-9-]/g, ''))
        }
    }, [title, isEdit, setValue]);

    const onSubmit = (data: FormValues) => {
        if (isEdit) {
            update.mutate({id: product.id, data}, {
                onSuccess: () => {
                    onClose();
                    reset()
                }
            })
        } else {
            create.mutate(data, {
                onSuccess: () => {
                    onClose();
                    reset()
                }
            })
        }
    }

    return (
        <Sheet open={open} onOpenChange={(open) => !open && onClose()}>
            <SheetContent className="w-120 sm:max-w-120 overflow-y-auto">
                <SheetHeader className="mb-6">
                    <SheetTitle>{isEdit ? 'Edit Product' : 'New Product'}</SheetTitle>
                </SheetHeader>
                <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 px-1">
                    <div className="space-y-1">
                        <Label>Title</Label>
                        <Input {...register('title')} />
                        {errors.title && <p className="text-sm text-destructive">{errors.title.message}</p>}
                    </div>
                    <div className="space-y-1">
                        <Label>Slug</Label>
                        <Input {...register('slug')} />
                        {errors.slug && <p className="text-sm text-destructive">{errors.slug.message}</p>}
                    </div>
                    <div className="space-y-1">
                        <Label>Category Slug</Label>
                        <Input {...register('categorySlug')} placeholder="e.g electronics"/>
                    </div>
                    <div className="space-y-1">
                        <Label>Price (VND)</Label>
                        <Input type="number"
                               {...register('priceCents', {valueAsNumber: true})} />
                        {errors.priceCents && <p className="text-sm text-destructive">{errors.priceCents.message}</p> }
                    </div>
                    <div className="space-y-1">
                        <ShopreeSelect control={control} name="status" label="Status" options={[
                            { value: 'DRAFT', label: 'Draft' },
                            { value: 'PUBLISHED', label: 'Published' },
                            { value: 'DISABLED', label: 'Disabled' },
                            { value: 'PENDING_APPROVAL', label: 'Pending Approval' },
                        ]} />
                    </div>
                    <div className="flex items-center gap-2">
                        <ShopreeCheckbox control={control} name="pickupAvailable" label="Pickup available" />
                    </div>
                    <SheetFooter>
                        <Button type="button" variant="outline" onClick={onClose}>Cancel</Button>
                        <Button type="submit" disabled={isPending}>
                            {isPending ? 'Saving...' : isEdit ? 'Save' : 'Create'}
                        </Button>
                    </SheetFooter>
                </form>
            </SheetContent>
        </Sheet>
    )
}