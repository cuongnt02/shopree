import {useQuery} from "@tanstack/react-query";
import {fetchVendorProfile} from "@/features/profile/api.ts";

export function useVendorProfile() {
    return useQuery({queryKey: ['vendor-profile'], queryFn: fetchVendorProfile})
}