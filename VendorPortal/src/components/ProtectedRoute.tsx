import * as React from "react";
import {useAuth} from "@/features/auth/AuthContext.tsx";
import {Navigate} from "react-router";

export function ProtectedRoute({children}: {children: React.ReactNode}) {
    const {isAuthenticated} = useAuth()
    return isAuthenticated ? <>{children}</> : <Navigate to="/login" replace />
}