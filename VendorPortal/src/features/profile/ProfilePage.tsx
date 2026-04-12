import {z} from "zod";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {useVendorProfile} from "@/features/profile/useVendorProfile.ts";
import {useEffect} from "react";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import {Badge} from "@/components/ui/badge.tsx";
import {useUpdateVendorProfile} from "@/features/profile/useUpdateVendorProfile.ts";
import {Input} from "@/components/ui/input.tsx";
import {Textarea} from "@/components/ui/textarea.tsx";
import {ShopreeCheckbox} from "@/components/shopree/ShopreeCheckbox.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Field, FieldError, FieldGroup, FieldLabel} from "@/components/ui/field.tsx";
import {InputGroup, InputGroupAddon, InputGroupInput, InputGroupText} from "@/components/ui/input-group.tsx";

const schema = z.object({
    vendorName: z.string().min(1, 'Store name is required'),
    description: z.string(),
    pickupAvailable: z.boolean(),
    localDeliveryRadiusKm: z.number().positive('Must be > 0'),
    addressStreet: z.string(),
    addressCity: z.string(),
    addressWard: z.string()
})

type FormValues = z.infer<typeof schema>

const statusVariant: Record<string, 'default' | 'secondary' | 'outline' | 'destructive'> = {
    APPROVED: 'default',
    PENDING: 'outline',
    REJECTED: 'destructive',
    SUSPENDED: 'secondary',
}

export function ProfilePage() {
    const {data, isPending, isError} = useVendorProfile()
    const update = useUpdateVendorProfile()

    const {register, handleSubmit, reset, control, formState: {errors}} = useForm<FormValues>({
        resolver: zodResolver(schema),
        defaultValues: {
            vendorName: '', description: '', pickupAvailable: false,
            localDeliveryRadiusKm: 10, addressStreet: '', addressCity: '', addressWard: ''
        }
    })

    useEffect(() => {
        if (data) {
            reset({
                vendorName: data.vendorName,
                description: data.description ?? '',
                pickupAvailable: data.pickupAvailable,
                localDeliveryRadiusKm: data.localDeliveryRadiusKm ?? 10,
                addressStreet: data.address?.street ?? '',
                addressCity: data.address?.city ?? '',
                addressWard: data.address?.ward ?? ''
            })
        }
    }, [data, reset])

    const onSubmit = (values: FormValues) => {
        update.mutate(values)
    }

    if (isPending) return <ProfileSkeleton/>
    if (isError) return <p className="text-destructive">Failed to load store profile</p>

    return (
        <div className="space-y-6">
            <h1 className="text-2xl font-semibold">Store Profile</h1>

            <Card>
                <CardContent className="pt-6 flex items-start justify-between">
                    <div>
                        <p className="text-xl font-semibold">{data.vendorName}</p>
                        <p className="text-sm text-muted-foreground">/{data.slug}</p>
                    </div>
                    <Badge variant={statusVariant[data.status]}>{data.status}</Badge>
                </CardContent>
            </Card>

            <Card>
                <CardHeader><CardTitle>Edit Profile</CardTitle></CardHeader>
                <CardContent>
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <FieldGroup>
                            <Field data-invalid={!!errors.vendorName}>
                                <FieldLabel>Store Name</FieldLabel>
                                <Input {...register('vendorName')} />
                                <FieldError errors={[errors.vendorName]}/>
                            </Field>
                            <Field>
                                <FieldLabel>Description</FieldLabel>
                                <Textarea {...register('description')} rows={3}/>
                            </Field>
                            <div className="space-y-2">
                                <p className="text-sm font-medium">Address</p>
                                <Field>
                                    <FieldLabel>Street</FieldLabel>
                                    <Input {...register('addressStreet')} placeholder="1 Nguyen Hue"/>
                                </Field>
                                <div className="grid grid-cols-2 gap-3">
                                    <Field>
                                        <FieldLabel>Ward</FieldLabel>
                                        <Input {...register('addressWard')} placeholder="Phuong Ben Nghe"/>
                                    </Field>
                                    <Field>
                                        <FieldLabel>City</FieldLabel>
                                        <Input {...register('addressCity')} placeholder="Ho Chi Minh City"/>
                                    </Field>
                                </div>
                            </div>
                            <ShopreeCheckbox control={control} name="pickupAvailable" label="Pickup available"/>
                            <Field data-invalid={!!errors.localDeliveryRadiusKm}>
                                <FieldLabel>Delivery Radius</FieldLabel>
                                <InputGroup>
                                    <InputGroupInput type="number"
                                                     {...register('localDeliveryRadiusKm', {valueAsNumber: true})}/>
                                    <InputGroupAddon align="inline-end">
                                        <InputGroupText>km</InputGroupText>
                                    </InputGroupAddon>
                                </InputGroup>
                                <FieldError errors={[errors.localDeliveryRadiusKm]}/>
                            </Field>
                            <div className="flex justify-end">
                                <Button type="submit" disabled={update.isPending}>
                                    {update.isPending ? 'Saving...' : 'Save'}
                                </Button>
                            </div>
                        </FieldGroup>
                    </form>
                </CardContent>
            </Card>
        </div>
    )
}

function ProfileSkeleton() {
    return (
        <div className="space-y-6">
            <Skeleton className="h-8 w-40"/>
            <Card><CardContent className="pt-6"><Skeleton className="h-16 w-full"/></CardContent></Card>
            <Card><CardContent className="pt-6 space-y-4">
                {Array.from({length: 5}).map((_, i) => <Skeleton key={i} className="h-9 w-full"/>)}
            </CardContent></Card>
        </div>
    )
}
