import { BrowserRouter, Route, Routes } from "react-router-dom";

import ProtectedRoute from "../components/auth/ProtectedRoutes";
import AdminLayout from "../layout/AdminLayout";
import AdminDashboard from "../pages/admin/AdminDashboard";
import Categories from "../pages/admin/Categories";
import Medicines from "../pages/admin/Medicines";
import Orders from "../pages/admin/Orders";
import Home from "../pages/Home";
import Login from "../pages/Login";
import Register from "../pages/Register";

function AppRoutes() {

    return (

        <BrowserRouter>

            <Routes>

                {/* Public Routes */}

                <Route
                    path="/"
                    element={<Home />}
                />

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                {/* Admin Routes */}

                <Route
                    path="/admin"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN"]}>

                            <AdminLayout />

                        </ProtectedRoute>
                    }
                >

                    <Route
                        path="dashboard"
                        element={<AdminDashboard />}
                    />

                    <Route 
                       path="medicines"
                       element={<Medicines />}

                    />   

                    <Route 
                     path="categories" 
                     element={<Categories />} />

                     <Route
                      path="orders"
                      element={<Orders />} />

                    </Route>

                <Route
                    path="*"
                    element={<h1>404 - Page Not Found</h1>}
                />

            </Routes>

        </BrowserRouter>

    );

}

export default AppRoutes;