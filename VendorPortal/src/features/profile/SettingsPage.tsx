import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Field, FieldError, FieldGroup, FieldLabel} from "@/components/ui/field.tsx";
import {useChangePassword} from "@/features/profile/useChangePassword.ts";
import axios from "axios";

const schema = z.object({
    currentPassword: z.string().min(1, 'Required'),
    newPassword: z.string().min(8, 'At least 8 characters'),
    confirmPassword: z.string()
}).refine(d => d.newPassword === d.confirmPassword, {
    message: 'Passwords do not match',
    path: ['confirmPassword']
})

type FormValues = z.infer<typeof schema>

export function SettingsPage() {
    const changePassword = useChangePassword()
    const {register, handleSubmit, reset, setError, formState: {errors}} = useForm<FormValues>({
        resolver: zodResolver(schema),
        defaultValues: {currentPassword: '', newPassword: '', confirmPassword: ''}
    })

    const onSubmit = (values: FormValues) => {
        changePassword.mutate(
            {currentPassword: values.currentPassword, newPassword: values.newPassword},
            {
                onSuccess: () => reset(),
                onError: (err) => {
                    const message = axios.isAxiosError(err)
                        ? err.response?.data?.message ?? 'Something went wrong'
                        : 'Something went wrong'
                    setError('currentPassword', {message})
                }
            }
        )
    }

    return (
        <div className="space-y-6">
            <h1 className="text-2xl font-semibold">Settings</h1>
            <Card>
                <CardHeader><CardTitle>Change Password</CardTitle></CardHeader>
                <CardContent>
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <FieldGroup>
                            <Field data-invalid={!!errors.currentPassword}>
                                <FieldLabel>Current Password</FieldLabel>
                                <Input type="password" {...register('currentPassword')}/>
                                <FieldError errors={[errors.currentPassword]}/>
                            </Field>
                            <Field data-invalid={!!errors.newPassword}>
                                <FieldLabel>New Password</FieldLabel>
                                <Input type="password" {...register('newPassword')}/>
                                <FieldError errors={[errors.newPassword]}/>
                            </Field>
                            <Field data-invalid={!!errors.confirmPassword}>
                                <FieldLabel>Confirm New Password</FieldLabel>
                                <Input type="password" {...register('confirmPassword')}/>
                                <FieldError errors={[errors.confirmPassword]}/>
                            </Field>
                            <div className="flex justify-end">
                                <Button type="submit" disabled={changePassword.isPending}>
                                    {changePassword.isPending ? 'Saving...' : 'Change Password'}
                                </Button>
                            </div>
                        </FieldGroup>
                    </form>
                </CardContent>
            </Card>
        </div>
    )
}