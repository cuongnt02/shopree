import {type Control, Controller, type FieldValues, type Path} from "react-hook-form";
import {Checkbox} from "@/components/ui/checkbox.tsx";
import {Label} from "@/components/ui/label.tsx";

interface Props<T extends FieldValues> {
    control: Control<T>
    name: Path<T>
    label: string
}

export function ShopreeCheckbox<T extends FieldValues>({control, name, label}: Props<T>) {
    return (
        <Controller control={control} name={name} render={({field}) => (
            <div className="flex items-center gap-2">
                <Checkbox id={name} checked={field.value} onCheckedChange={field.onChange}/>
                <Label htmlFor={name}>{label}</Label>
            </div>
        )}/>
    )
}

