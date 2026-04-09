export type UserRole = 'BUYER' | 'VENDOR_USER' | 'ADMIN'

export interface LoginRequest {
    email: string,
    password: string
}

export interface AuthTokens {
    accessToken: string
    refreshToken: string
    expiresAt: number
    userId: string
    role: UserRole
}
