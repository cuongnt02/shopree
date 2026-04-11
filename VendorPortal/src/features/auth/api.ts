import type {AuthTokens, LoginRequest} from "@/types/auth.ts";
import {api} from "@/lib/axios.ts";

export async function login(body: LoginRequest): Promise<AuthTokens> {
    const {data} = await api.post<AuthTokens>('/api/v1/auth/login', body)
    return data
}

export async function logout(refreshToken: string): Promise<void> {
    await api.post('/api/v1/auth/logout', {refreshToken})
}