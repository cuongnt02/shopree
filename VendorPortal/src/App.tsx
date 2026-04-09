import {Outlet, Route, Routes} from "react-router";
import {DashboardLayout} from "@/components/layout/DashboardLayout.tsx";
import {OrdersPage} from "@/features/orders/OrdersPage.tsx";
import {ProtectedRoute} from "@/components/ProtectedRoute.tsx";
import {LoginPage} from "@/features/auth/LoginPage.tsx";
import {DashboardPage} from "@/features/dashboard/DashboardPage.tsx";

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
                <Route path="/products" element={<div>Products</div>}/>
            </Route>
        </Routes>


    )
}