import type {AuthTokens} from "@/types/auth.ts";
import {createContext, useCallback, useContext, useState} from "react";
import * as React from "react";

interface AuthContextValue {
    tokens: AuthTokens | null,
    saveTokens: (tokens: AuthTokens) => void
    clearTokens: () => void,
    isAuthenticated: boolean
}

const AuthContext = createContext<AuthContextValue | null>(null)

const STORAGE_KEY = 'vendor_auth'

function loadTokens(): AuthTokens | null {
    try {
        const raw = localStorage.getItem(STORAGE_KEY)
        return raw ? (JSON.parse(raw) as AuthTokens) : null
    } catch {
        return null
    }
}

export function AuthProvider({children}: {children: React.ReactNode}) {
    const [tokens, setTokens] = useState<AuthTokens | null>(loadTokens())

    const saveTokens = useCallback((t: AuthTokens) => {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(t))
        setTokens(t)
    }, [])

    const clearTokens = useCallback(() => {
        localStorage.removeItem(STORAGE_KEY)
        setTokens(null)
    }, [])

    return (
            <AuthContext.Provider value={{ tokens, saveTokens, clearTokens, isAuthenticated: !!tokens}}>
                {children}
            </AuthContext.Provider>
    )
}

export function useAuth() {
    const ctx = useContext(AuthContext)
    if (!ctx) throw new Error('useAuth must be used inside AuthProvider')
    return ctx
}