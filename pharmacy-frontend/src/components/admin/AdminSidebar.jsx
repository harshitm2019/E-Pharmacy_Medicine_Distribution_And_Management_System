import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import { Box, Divider, List, ListItemButton, ListItemIcon, ListItemText, Typography } from "@mui/material";
import { NavLink } from "react-router-dom";

import { ADMIN_MENUS, LOGOUT_MENU } from "../../constants/adminMenus";

const SIDEBAR_WIDTH = 260;

function AdminSidebar() {

    return (

        <Box
            sx={{
                width: SIDEBAR_WIDTH,
                height: "100vh",
                bgcolor: "#FFFFFF",
                borderRight: "1px solid #E5E7EB",
                display: "flex",
                flexDirection: "column",
                position: "sticky",
                top: 0,
            }}
        >

            {/* Logo */}

            <Box
                sx={{
                    height: 80,
                    display: "flex",
                    alignItems: "center",
                    gap: 1.5,
                    px: 3,
                    background: "linear-gradient(135deg,#4CAF50,#2E7D32)"
                }}
            >

                <LocalPharmacyIcon
                    sx={{
                        color: "#FFFFFF",
                        fontSize: 34
                    }}
                />

                <Box>

                    <Typography
                        sx={{
                            color: "#FFFFFF",
                            fontWeight: 800,
                            fontSize: "22px",
                            lineHeight: 1
                        }}
                    >
                        ePharmacy
                    </Typography>

                    <Typography
                        sx={{
                            color: "rgba(255,255,255,.8)",
                            fontSize: "12px"
                        }}
                    >
                        Admin Panel
                    </Typography>

                </Box>

            </Box>

            {/* Menu */}

            <List sx={{ px: 2, py: 2, flex: 1 }}>

                {
                    ADMIN_MENUS.map(menu => (

                        <ListItemButton
                            key={menu.title}
                            component={NavLink}
                            to={menu.path}
                            sx={{
                                mb: 1,
                                borderRadius: "12px",
                                color: "#455A64",

                                "& .MuiListItemIcon-root": {
                                    color: "inherit",
                                    minWidth: 42
                                },

                                "&.active": {
                                    bgcolor: "#E8F5E9",
                                    color: "#2E7D32",
                                    fontWeight: 700
                                },

                                "&:hover": {
                                    bgcolor: "#F5F7FA"
                                }
                            }}
                        >

                            <ListItemIcon>

                                {menu.icon}

                            </ListItemIcon>

                            <ListItemText primary={menu.title} />

                        </ListItemButton>

                    ))
                }

            </List>

            <Divider />

            {/* Logout */}

            <Box sx={{ p: 2 }}>

                <ListItemButton
                    sx={{
                        borderRadius: "12px",
                        color: "#D32F2F",

                        "&:hover": {
                            bgcolor: "#FFEBEE"
                        }
                    }}
                >
                    <ListItemIcon
                        sx={{
                            color: "#D32F2F",
                            minWidth: 42
                        }}
                    >
                        {LOGOUT_MENU.icon}

                    </ListItemIcon>

                    <ListItemText primary={LOGOUT_MENU.title} />
                </ListItemButton>
            </Box>
        </Box>
    );
}
export default AdminSidebar;