import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";

import AdminHeader from "../components/admin/AdminHeader";
import AdminSidebar from "../components/admin/AdminSidebar";

const SIDEBAR_WIDTH = 260;

function AdminLayout() {

    return (

        <Box
            sx={{
                display: "flex",
                minHeight: "100vh",
                bgcolor: "#F5F7FA"
            }}
        >

            <AdminSidebar />

            <Box
                sx={{
                    flex: 1,
                    display: "flex",
                    flexDirection: "column",
                    minWidth: 0
                }}
            >

                <AdminHeader />

                <Box
                    sx={{
                        flex: 1,
                        p: 4,
                        overflow: "auto"
                    }}
                >
                    <Outlet />
                </Box>
            </Box>
        </Box>
    ); 
}
export default AdminLayout;