import {useMutation, useQueryClient} from "@tanstack/react-query";
import {updateVendorProfile} from "@/features/profile/api.ts";

export function useUpdateVendorProfile() {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: updateVendorProfile,
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['vendor-profile']})
    })
}