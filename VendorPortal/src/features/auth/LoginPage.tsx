import {z} from "zod";
import {useAuth} from "@/features/auth/AuthContext.tsx";
import {useNavigate} from "react-router";
import {Controller, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {useMutation} from "@tanstack/react-query";
import {login} from "@/features/auth/api.ts";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Field, FieldError, FieldLabel} from "@/components/ui/field.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Button} from "@/components/ui/button.tsx";

const schema = z.object({
    email: z.email('Enter a valid email'),
    password: z.string().min(1, 'Password is required')
})

type FormValues = z.infer<typeof schema>

export function LoginPage() {
    const {saveTokens} = useAuth()
    const navigate = useNavigate()

    const form = useForm<FormValues>({
        resolver: zodResolver(schema),
        defaultValues: {email: '', password: ''},
    })

    const mutation = useMutation({
        mutationFn: login,
        onSuccess: (tokens) => {
            if (tokens.role !== 'VENDOR_USER' && tokens.role !== 'ADMIN') {
                form.setError('root', {message: 'Access restricted to vendors only.'})
                return
            }
            saveTokens(tokens)
            navigate('/', {replace: true})
        },
        onError: () => {
            form.setError('root', {message: 'Invalid email or password'})
        }
    })

    return (
        <div className="min-h-screen flex items-center justify-center bg-muted/40">
            <Card className="w-full max-w-sm">
                <CardHeader>
                    <CardTitle className="text-xl">Vendor Portal</CardTitle>
                </CardHeader>
                <CardContent>
                    <form onSubmit={form.handleSubmit((v) => mutation.mutate(v))} className="space-y-4">
                        <Controller name="email" control={form.control} render={({field, fieldState}) => (
                            <Field data-invalid={fieldState.invalid}>
                                <FieldLabel htmlFor={field.name}>Email</FieldLabel>
                                <Input {...field} id={field.name} type="email" placeholder="vendor@example.com"
                                       aria-invalid={fieldState.invalid}/>
                                {fieldState.invalid && <FieldError errors={[fieldState.error]}/>}
                            </Field>
                        )}/>
                        <Controller name="password" control={form.control} render={({field, fieldState}) => (
                            <Field data-invalid={fieldState.invalid}>
                                <FieldLabel htmlFor={field.name}>Password</FieldLabel>
                                <Input
                                    {...field}
                                    id={field.name}
                                    type="password"
                                    aria-invalid={fieldState.invalid}
                                />
                                {fieldState.invalid && <FieldError errors={[fieldState.error]} />}
                            </Field>
                        )} />
                        {form.formState.errors.root && (
                            <p className="text-sm text-destructive">{form.formState.errors.root.message}</p>
                        )}
                        <Button type="submit" className="w-full" disabled={mutation.isPending}>
                            {mutation.isPending ? 'Signing in...' : 'Sign in'}
                        </Button>
                    </form>
                </CardContent>
            </Card>
        </div>
    )


}