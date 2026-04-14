import {useQuery} from "@tanstack/react-query";
import {fetchCategories} from "@/features/products/api.ts";

export function useCategories() {
    return useQuery({queryKey: ['categories'], queryFn: fetchCategories})
}