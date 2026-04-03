import {Route, Routes} from "react-router";
import {DashboardLayout} from "@/components/layout/DashboardLayout.tsx";

function DashboardPage() {
    return <h1 className="text-2xl font-bold">Dashboard</h1>
}

export default function App() {
  return (
      <DashboardLayout>
          <Routes>
              <Route path="/" element={ <DashboardPage /> } />
              <Route path="/orders" element={ <div>Orders</div> } />
              <Route path="/products" element={<div>Products</div> } />

          </Routes>
      </DashboardLayout>

  )
}