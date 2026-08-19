import MenuIcon from "@mui/icons-material/Menu";
import { Box, IconButton } from "@mui/material";
import { useState } from "react";
import { Outlet } from "react-router-dom";
import AdminSidebar from "../components/admin/AdminSidebar";
import Header from "../components/common/Header";

function AdminLayout() {
    const [mobileOpen, setMobileOpen] = useState(false);

    return (
        <Box sx={{ display: "flex", minHeight: "100vh", bgcolor: "#F5F7FA" }}>
            <AdminSidebar mobileOpen={mobileOpen} onMobileClose={() => setMobileOpen(false)} />

            <Box sx={{ flex: 1, display: "flex", flexDirection: "column", minWidth: 0 }}>
                <Box sx={{ display: { xs: "flex", md: "none" }, alignItems: "center", height: 56, px: 1, bgcolor: "#FFFFFF", borderBottom: "1px solid #E5E7EB" }}>
                    <IconButton onClick={() => setMobileOpen(true)}>
                        <MenuIcon />
                    </IconButton>
                </Box>

                <Header />

                <Box sx={{ flex: 1, p: { xs: 2, md: 4 }, overflow: "auto", minWidth: 0 }}>
                    <Outlet />
                </Box>

            </Box>
        </Box>
    );
}
export default AdminLayout;