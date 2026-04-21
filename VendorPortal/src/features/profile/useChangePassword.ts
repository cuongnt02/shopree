import {useMutation} from "@tanstack/react-query";
import {changePassword} from "@/features/profile/api.ts";

export function useChangePassword() {
    return useMutation({mutationFn: changePassword})
}