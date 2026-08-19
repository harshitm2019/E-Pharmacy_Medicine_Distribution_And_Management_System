import CloseIcon from "@mui/icons-material/Close";
import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import { Box, Divider, Drawer, IconButton, List, ListItemButton, ListItemIcon, ListItemText, Typography } from "@mui/material";
import { NavLink } from "react-router-dom";
import { CUSTOMER_MENUS, LOGOUT_MENU } from "../../constants/customerMenus";
import useLogout from "../../hooks/useLogout";

const SIDEBAR_WIDTH = 260;

function SidebarContent({ onClose }) {
    const logout = useLogout();

    return (
        <Box sx={{ width: SIDEBAR_WIDTH, height: "100%", bgcolor: "#FFFFFF", display: "flex", flexDirection: "column" }}>
            <Box sx={{ height: 80, display: "flex", alignItems: "center", gap: 1.5, px: 3, background: "linear-gradient(135deg,#4CAF50,#2E7D32)" }}>
                <LocalPharmacyIcon sx={{ color: "#FFFFFF", fontSize: 34 }} />
                <Box>
                    <Typography sx={{ color: "#FFFFFF", fontWeight: 800, fontSize: "22px", lineHeight: 1 }}>ePharmacy</Typography>
                    <Typography sx={{ color: "rgba(255,255,255,.8)", fontSize: "12px" }}>Customer Panel</Typography>
                </Box>
                <IconButton onClick={onClose} sx={{ ml: "auto", color: "#FFFFFF", display: { xs: "block", md: "none" } }}>
                    <CloseIcon />
                </IconButton>
            </Box>

            <List sx={{ px: 2, py: 2, flex: 1 }}>
                {CUSTOMER_MENUS.map(menu => (
                    <ListItemButton key={menu.title} component={NavLink} to={menu.path} onClick={onClose} sx={{ mb: 1, borderRadius: "12px", color: "#455A64", "& .MuiListItemIcon-root": { color: "inherit", minWidth: 42 }, "&.active": { bgcolor: "#E8F5E9", color: "#2E7D32", fontWeight: 700 }, "&:hover": { bgcolor: "#F5F7FA" } }}>
                        <ListItemIcon>{menu.icon}</ListItemIcon>
                        <ListItemText primary={menu.title} />
                    </ListItemButton>
                ))}
            </List>

            <Divider />

            <Box sx={{ p: 2 }}>
                <ListItemButton onClick={logout} sx={{ borderRadius: "12px", color: "#D32F2F", "&:hover": { bgcolor: "#FFEBEE" } }}>
                    <ListItemIcon sx={{ color: "#D32F2F", minWidth: 42 }}>
                        {LOGOUT_MENU.icon}
                    </ListItemIcon>
                    <ListItemText primary={LOGOUT_MENU.title} />
                </ListItemButton>
            </Box>
        </Box>
    );
}

function CustomerSidebar({ mobileOpen, onMobileClose }) {
    return (
        <>
            <Box sx={{ display: { xs: "none", md: "block" }, width: SIDEBAR_WIDTH, flexShrink: 0 }}>
                <Box sx={{ width: SIDEBAR_WIDTH, height: "100vh", position: "sticky", top: 0 }}>
                    <SidebarContent />
                </Box>
            </Box>

            <Drawer anchor="left" open={mobileOpen} onClose={onMobileClose} sx={{ display: { xs: "block", md: "none" }, "& .MuiDrawer-paper": { width: SIDEBAR_WIDTH } }}>
                <SidebarContent onClose={onMobileClose} />
            </Drawer>
        </>
    );
}

export default CustomerSidebar;