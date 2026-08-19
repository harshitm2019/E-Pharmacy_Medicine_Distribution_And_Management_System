import MenuIcon from "@mui/icons-material/Menu";
import { Box, IconButton } from "@mui/material";
import { useState } from "react";
import { Outlet } from "react-router-dom";

import Header from "../components/common/Header";
import CustomerSidebar from "../components/customer/CustomerSidebar";

function CustomerLayout() {
    const [mobileOpen, setMobileOpen] = useState(false);

    return (
        <Box sx={{ display: "flex", minHeight: "100vh", bgcolor: "#F5F7FA" }}>
            <CustomerSidebar mobileOpen={mobileOpen} onMobileClose={() => setMobileOpen(false)} />

            <Box sx={{ flex: 1, minWidth: 0, display: "flex", flexDirection: "column" }}>
                <Header />

                <IconButton onClick={() => setMobileOpen(true)} sx={{ display: { xs: "flex", md: "none" }, position: "fixed", top: 18, left: 12, zIndex: 1300, bgcolor: "#FFFFFF", boxShadow: 2 }}>
                    <MenuIcon />
                </IconButton>

                <Box sx={{ flex: 1, p: { xs: 2, sm: 3, md: 4 } }}>
                    <Outlet />
                </Box>
            </Box>
        </Box>
    );
}
export default CustomerLayout;