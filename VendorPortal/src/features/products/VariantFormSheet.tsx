import type {ProductVariant} from "@/types/product.ts";
import {useAddVariant} from "@/features/products/useAddVariant.ts";
import {useUpdateVariant} from "@/features/products/useUpdateVariant.ts";
import {useUploadVariantImage} from "@/features/products/useUploadVariantImage.ts";
import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {useEffect} from "react";
import {Sheet, SheetContent, SheetFooter, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Field, FieldError, FieldGroup, FieldLabel} from "@/components/ui/field.tsx";
import {InputGroup, InputGroupAddon, InputGroupInput, InputGroupText} from "@/components/ui/input-group.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Camera} from "lucide-react";

const schema = z.object({
    title: z.string(),
    sku: z.string(),
    priceCents: z.number({error: 'Price is required'}).positive('Must be > 0'),
    compareAtCents: z.number().min(0),
    inventoryCount: z.number().int().min(0, 'Must be 0 or more'),
})

type FormValues = z.infer<typeof schema>

interface Props {
    open: boolean
    onClose: () => void
    productId: string
    variant?: ProductVariant
}

export function VariantFormSheet({open, onClose, productId, variant}: Props) {
    const isEdit = variant !== undefined
    const add = useAddVariant(productId)
    const update = useUpdateVariant(productId)
    const uploadImage = useUploadVariantImage(productId)
    const isPending = add.isPending || update.isPending

    const {register, handleSubmit, reset, formState: {errors}} = useForm<FormValues>({
        resolver: zodResolver(schema),
        defaultValues: {title: '', sku: '', priceCents: 0, compareAtCents: 0, inventoryCount: 0}
    })

    useEffect(() => {
        if (open) {
            reset(isEdit ? {
                title: variant?.title ?? '',
                sku: variant?.sku ?? '',
                priceCents: variant?.priceCents ?? 0,
                compareAtCents: variant?.compareAtCents ?? 0,
                inventoryCount: variant?.inventoryCount ?? 0,
            } : {title: '', sku: '', priceCents: 0, compareAtCents: 0, inventoryCount: 0})
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [open])

    const onSubmit = (data: FormValues) => {
        if (isEdit) {
            update.mutate({variantId: variant.id, data}, {onSuccess: () => { onClose(); reset() }})
        } else {
            add.mutate(data, {onSuccess: () => { onClose(); reset() }})
        }
    }

    return (
        <Sheet open={open} onOpenChange={(open) => !open && onClose()}>
            <SheetContent className="w-120 sm:max-w-120 overflow-y-auto">
                <SheetHeader className="mb-6">
                    <SheetTitle>{isEdit ? 'Edit Variant' : 'New Variant'}</SheetTitle>
                </SheetHeader>
                {isEdit && (
                    <div className="px-1 mb-6">
                        <p className="text-sm font-medium mb-3">Image</p>
                        <div className="flex items-center gap-4">
                            {variant.image
                                ? <img src={variant.image} alt={variant.title ?? ''}
                                       className="w-20 h-20 rounded object-cover"/>
                                : <div className="w-20 h-20 rounded bg-muted flex items-center justify-center">
                                    <Camera size={20} className="text-muted-foreground"/>
                                </div>
                            }
                            <label className="cursor-pointer">
                                <input type="file" accept="image/*" className="hidden"
                                       onChange={(e) => {
                                           const file = e.target.files?.[0]
                                           if (file) {
                                               uploadImage.mutate({variantId: variant.id, file})
                                               e.target.value = ''
                                           }
                                       }}/>
                                <Button variant="outline" size="sm" asChild>
                                    <span>{uploadImage.isPending ? 'Uploading...' : variant.image ? 'Change Image' : 'Upload Image'}</span>
                                </Button>
                            </label>
                        </div>
                    </div>
                )}
                <form onSubmit={handleSubmit(onSubmit)} className="px-1">
                    <FieldGroup>
                        <Field>
                            <FieldLabel>
                                Title <span className="text-muted-foreground font-normal">(optional)</span>
                            </FieldLabel>
                            <Input {...register('title')} placeholder="e.g. Large, Red, 256GB"/>
                        </Field>
                        <Field>
                            <FieldLabel>
                                SKU <span className="text-muted-foreground font-normal">(optional)</span>
                            </FieldLabel>
                            <Input {...register('sku')} placeholder="e.g. PROD-001"/>
                        </Field>
                        <Field data-invalid={!!errors.priceCents}>
                            <FieldLabel>Price</FieldLabel>
                            <InputGroup>
                                <InputGroupInput type="number"
                                                 {...register('priceCents', {valueAsNumber: true})}/>
                                <InputGroupAddon align="inline-end">
                                    <InputGroupText>₫</InputGroupText>
                                </InputGroupAddon>
                            </InputGroup>
                            <FieldError errors={[errors.priceCents]}/>
                        </Field>
                        <Field data-invalid={!!errors.compareAtCents}>
                            <FieldLabel>
                                Compare-at Price <span className="text-muted-foreground font-normal">(optional, 0 = none)</span>
                            </FieldLabel>
                            <InputGroup>
                                <InputGroupInput type="number"
                                                 {...register('compareAtCents', {valueAsNumber: true})}/>
                                <InputGroupAddon align="inline-end">
                                    <InputGroupText>₫</InputGroupText>
                                </InputGroupAddon>
                            </InputGroup>
                            <FieldError errors={[errors.compareAtCents]}/>
                        </Field>
                        <Field data-invalid={!!errors.inventoryCount}>
                            <FieldLabel>Inventory</FieldLabel>
                            <Input type="number" {...register('inventoryCount', {valueAsNumber: true})}/>
                            <FieldError errors={[errors.inventoryCount]}/>
                        </Field>
                        <SheetFooter>
                            <Button type="button" variant="outline" onClick={onClose}>Cancel</Button>
                            <Button type="submit" disabled={isPending}>
                                {isPending ? 'Saving...' : isEdit ? 'Save' : 'Add'}
                            </Button>
                        </SheetFooter>
                    </FieldGroup>
                </form>
            </SheetContent>
        </Sheet>
    )
}
