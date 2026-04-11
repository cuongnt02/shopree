import {type Control, Controller, type FieldValues, type Path} from "react-hook-form";
import {Label} from "@/components/ui/label.tsx";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select.tsx";

interface SelectOption {
    label: string
    value: string
}

interface Props<T extends FieldValues> {
    control: Control<T>
    name: Path<T>
    label?: string
    options: SelectOption[]
    placeholder?: string
}

export function ShopreeSelect<T extends FieldValues>({control, name, label, options, placeholder}: Props<T>) {
    return (
        <div className="space-y-1">
            {label && <Label>{label}</Label>}
            <Controller control={control} name={name} render={({field}) => (
                <Select value={field.value} onValueChange={field.onChange}>
                    <SelectTrigger className="w-full">
                        <SelectValue placeholder={placeholder} />
                    </SelectTrigger>
                    <SelectContent>
                        {options.map((opt) => (
                            <SelectItem value={opt.value} key={opt.value}>{opt.label}</SelectItem>
                        ))}
                    </SelectContent>
                </Select>
            )} />
        </div>
    )
}