import {Outlet, Route, Routes} from "react-router";
import {DashboardLayout} from "@/components/layout/DashboardLayout.tsx";
import {OrdersPage} from "@/features/orders/OrdersPage.tsx";
import {ProtectedRoute} from "@/components/ProtectedRoute.tsx";
import {LoginPage} from "@/features/auth/LoginPage.tsx";
import {DashboardPage} from "@/features/dashboard/DashboardPage.tsx";
import {ProductsPage} from "@/features/products/ProductsPage.tsx";
import {ProfilePage} from "@/features/profile/ProfilePage.tsx";
import {SettingsPage} from "@/features/profile/SettingsPage.tsx";

function ProtectedLayout() {
    return (
        <ProtectedRoute>
            <DashboardLayout>
                <Outlet/>
            </DashboardLayout>
        </ProtectedRoute>
    )
}

export default function App() {
    return (
        <Routes>
            <Route path="/login" element={<LoginPage/>}/>
            <Route element={<ProtectedLayout/>}>
                <Route path="/" element={<DashboardPage/>}/>
                <Route path="/orders" element={<OrdersPage/>}/>
                <Route path="/products" element={<ProductsPage />}/>
                <Route path="/profile" element={<ProfilePage />}/>
                <Route path="/settings" element={<SettingsPage />}/>
            </Route>
        </Routes>


    )
}