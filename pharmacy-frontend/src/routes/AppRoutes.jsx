import { BrowserRouter, Route, Routes } from "react-router-dom";

import ProtectedRoute from "../components/auth/ProtectedRoutes";
import AdminLayout from "../layout/AdminLayout";
import CustomerLayout from "../layout/CustomerLayout";
import DeliveryLayout from "../layout/DeliveryLayout";
import AdminDashboard from "../pages/admin/AdminDashboard";
import Categories from "../pages/admin/Categories";
import DeliveryManagement from "../pages/admin/DeliveryManagement";
import Medicines from "../pages/admin/Medicines";
import OrderReport from "../pages/admin/OrderReport";
import Orders from "../pages/admin/Orders";
import Reports from "../pages/admin/Report";
import Returns from "../pages/admin/Returns";
import UserManagement from "../pages/admin/UserManagement";
import Cart from "../pages/customer/Cart";
import Checkout from "../pages/customer/Checkout";
import CustomerDashboard from "../pages/customer/CustomerDashboard";
import CustomerReturns from "../pages/customer/CustomerReturns";
import CustMedicines from "../pages/customer/Medicines";
import MyOrders from "../pages/customer/MyOrders";
import MyPayments from "../pages/customer/MyPayments";
import Prescriptions from "../pages/customer/Prescriptions";
import Deliveries from "../pages/delivery/Deliveries";
import DeliveryBoyDashboard from "../pages/delivery/DeliveryBoyDashboard";
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Settings from "../pages/Settings";

function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Public Routes */}
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />

                {/* Admin Routes */}
                <Route path="/admin" element={<ProtectedRoute allowedRoles={["ADMIN"]}><AdminLayout /></ProtectedRoute>}>
                    <Route path="dashboard" element={<AdminDashboard />} />
                    <Route path="medicines" element={<Medicines />} />
                    <Route path="categories" element={<Categories />} />
                    <Route path="orders" element={<Orders />} />
                    <Route path="user-management" element={<UserManagement />} />
                    <Route path="delivery-boys" element={<DeliveryManagement />} />
                    <Route path="returns" element={<Returns />} />
                    <Route path="reports" element={<Reports />} />
                    <Route path="reports/order-report" element={<OrderReport />} />
                    <Route path="settings" element={<Settings />} />
                </Route>

                {/* Customer Routes */}
                <Route path="/customer" element={<ProtectedRoute allowedRoles={["CUSTOMER"]}><CustomerLayout /></ProtectedRoute>}>
                    <Route path="dashboard" element={<CustomerDashboard />} />
                    <Route path="medicines" element={<CustMedicines />} />
                    <Route path="cart" element={<Cart />} />
                    <Route path="prescriptions" element={<Prescriptions />} />
                    <Route path="orders" element={<MyOrders />} />
                    <Route path="checkout" element={<Checkout />} />
                    <Route path="returns" element={<CustomerReturns />} />
                    <Route path="payments" element={<MyPayments />} />
                    <Route path="settings" element={<Settings />} />
                </Route>

                {/* Delivery Boy Routes */}
                <Route path="/delivery" element={<ProtectedRoute allowedRoles={["DELIVERY_BOY"]}><DeliveryLayout /></ProtectedRoute>}>
                    <Route path="dashboard" element={<DeliveryBoyDashboard />} />
                    <Route path="deliveries" element={<Deliveries />} />
                    <Route path="settings" element={<Settings />} />
                </Route>

                {/* Fallback 404 */}
                <Route path="*" element={<h1>404 - Page Not Found</h1>} />
            </Routes>
        </BrowserRouter>
    );
}

export default AppRoutes;
