import * as React from "react";
import {SidebarProvider, SidebarTrigger} from "@/components/ui/sidebar.tsx";
import {AppSidebar} from "@/components/layout/AppSidebar.tsx";

export function DashboardLayout({children}: {children: React.ReactNode}) {
    return (
        <SidebarProvider>
            <AppSidebar />
            <main className="flex-1 flex flex-col min-h-screen">
                <header className="h-12 border-b flex items-center px-4 gap-2">
                    <SidebarTrigger />
                </header>
                <div className="flex-1 p-6">{children}</div>
            </main>
        </SidebarProvider>
    )
}