import {
    Sidebar,
    SidebarContent,
    SidebarFooter,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem
} from "@/components/ui/sidebar.tsx";
import {LayoutDashboard, LogOut, Package, ShoppingBag, Store} from "lucide-react";
import {NavLink, useLocation, useNavigate} from "react-router";
import {useAuth} from "@/features/auth/AuthContext.tsx";
import {logout} from "@/features/auth/api.ts";

export function AppSidebar() {
    const location = useLocation()
    const navigate = useNavigate()
    const {tokens, clearTokens} = useAuth()

    const items = [
        {title: "Dashboard", url: "/", icon: LayoutDashboard},
        {title: "Orders", url: "/orders", icon: ShoppingBag},
        {title: "Products", url: "/products", icon: Package},
        {title: "Store Profile", url: '/profile', icon: Store}
    ]

    async function handleLogout() {
        try {
            if (tokens?.refreshToken) await logout(tokens.refreshToken)
        } finally {
            clearTokens()
            navigate('/login', {replace: true})
        }
    }

    return (
        <Sidebar>
            <SidebarContent>
                <SidebarGroup>
                    <SidebarGroupLabel>Vendor Portal</SidebarGroupLabel>
                    <SidebarGroupContent>
                        <SidebarMenu>
                            {items.map((item) => (
                                <SidebarMenuItem key={item.title}>
                                    <SidebarMenuButton asChild isActive={location.pathname === item.url}>
                                        <NavLink to={item.url}>
                                            <item.icon/>
                                            <span>{item.title}</span>
                                        </NavLink>
                                    </SidebarMenuButton>
                                </SidebarMenuItem>
                            ))}
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
            </SidebarContent>
            <SidebarFooter>
                <SidebarMenu>
                    <SidebarMenuItem>
                        <SidebarMenuButton onClick={handleLogout}>
                            <LogOut/>
                            <span>Logout</span>
                        </SidebarMenuButton>
                    </SidebarMenuItem>
                </SidebarMenu>
            </SidebarFooter>
        </Sidebar>
    );
}